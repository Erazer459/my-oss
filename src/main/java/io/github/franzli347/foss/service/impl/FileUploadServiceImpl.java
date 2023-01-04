package io.github.franzli347.foss.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.dto.FileUploadParam;
import io.github.franzli347.foss.exception.FileException;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.support.fileSupport.*;
import io.github.franzli347.foss.utils.FileUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文件上传处理service
 *
 * @author FranzLi
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    private final FilePathResolver filePathResolver;

    private final FileTransferResolver fileTransferResolver;

    private final ChunkPathResolver chunkPathResolver;

    private final FileUploadPostProcessorRegister fileUploadPostProcessorRegister;

    @Value("${pathMap.source}")
    private String filePath;

    @Value("${tmpFilePattern}")
    String tmpFilePattern;

    @Value("${downloadAddr}")
    String downloadAddr;

    public FileUploadServiceImpl(StringRedisTemplate stringRedisTemplate,
                                 ObjectMapper objectMapper,
                                 FilePathResolver filePathResolver,
                                 FileTransferResolver fileTransferResolver,
                                 ChunkPathResolver chunkPathResolver,
                                 FileUploadPostProcessorRegister fileUploadPostProcessorRegister) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.filePathResolver = filePathResolver;
        this.fileTransferResolver = fileTransferResolver;
        this.chunkPathResolver = chunkPathResolver;
        this.fileUploadPostProcessorRegister = fileUploadPostProcessorRegister;
    }

    /**
     * 创建上传任务
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public String initMultipartUpload(final int uid,
                                      final int bid,
                                      final String name,
                                      final int chunks,
                                      final String md5,
                                      final long size) {
        // 文件存在直接返回文件路径
        if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, md5)).orElse(false)) {
            return filePathResolver.getFilePath(md5);
        }
        // 生成任务id
        String taskId = String.valueOf(IdUtil.getSnowflake());

        FileUploadParam saveRedisData = new FileUploadParam(uid,
                taskId,
                chunks,
                size,
                bid,
                name,
                md5,
                LocalDateTime.now());

        initUploadDataToRedis(taskId, saveRedisData);

        // 返回任务id
        return taskId;
    }




    /**
     * 检查上传任务当前块数
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    public Set<String> check(String id) {
        // 查询任务是否存在
        checkTaskStatus(id);
        // 如果存在任务，返回已上传分片,不存在直接抛出异常
        return Objects.requireNonNull(stringRedisTemplate.opsForSet().members(RedisConstant.FILE_CHUNK_LIST + "_" + id))
                                .stream().filter(s -> !s.isBlank())
                                .collect(Collectors.toSet());
    }

    /**
     * 分块上传
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public String uploadChunk(final String id,
                              final int uid,
                              final int bid,
                              final String name,
                              final int chunks,
                              final int chunk,
                              final Long size,
                              final String md5,
                              final MultipartFile file) {

        // 检查任务id
        checkTaskStatus(id);

        // 转存分片 ( 最后path = filepath\tmp\12345.1.chunk
        fileTransferResolver.transferFile(file,tmpFilePattern.formatted(filePath,id,chunk));
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + id, String.valueOf(chunk));

        Long upLoadChunks = Optional.ofNullable(stringRedisTemplate.opsForSet().size(RedisConstant.FILE_CHUNK_LIST + "_" + id))
                .orElseThrow(() -> new FileException("task_not_exist"));
        //所有块都上传完成(+1是因为set中有一个空字符串)

        if (upLoadChunks == chunks + 1) {
            // 删除redis中的任务信息
            stringRedisTemplate.delete(List.of(RedisConstant.FILE_CHUNK_LIST + "_" + id, RedisConstant.FILE_TASK + "_" + id));
            //  添加 md5 信息到redis
            stringRedisTemplate.opsForSet().add(RedisConstant.FILE_MD5_LIST, md5);

            String resultPath = "%s%d\\%s".formatted(filePath, bid, name);
            List<String> collect = chunkPathResolver.getChunkPaths(id, chunks);

            boolean merge = FileUtil.mergeFiles(collect.toArray(new String[0]), resultPath);
            if (!merge) {
                throw new FileException("merge_file_error");
            }

            // 保存文件信息
            FileUploadParam saveData = new FileUploadParam(uid, id, chunks, size, bid, name, md5, LocalDateTime.now());

            doFilePostProcessor(resultPath, saveData);

            //返回信息
            return "%s/%d/%s".formatted(downloadAddr,bid,name);

        }
        return "upload[%s]chunk[%d]success".formatted(name,chunk);
    }

    private void doFilePostProcessor(String resultPath, FileUploadParam saveData) {
        List<FileUploadPostProcessor> fileUploadPostProcessors = fileUploadPostProcessorRegister.getFileUploadPostProcessors();
        for (FileUploadPostProcessor fileUploadPostProcessor : fileUploadPostProcessors) {
            boolean processResult = fileUploadPostProcessor.process(resultPath, saveData);
            if(!processResult) {
                throw new FileException("process_error");
            }
        }
    }

    private void checkTaskStatus(String id) throws JsonProcessingException {
        objectMapper.readValue(Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + id))
                .orElseThrow(() -> new RuntimeException("task_is_not_exist")),FileUploadParam.class);
    }


    @SneakyThrows
    private void initUploadDataToRedis(String taskId, FileUploadParam saveRedisData) {
        // 序列化任务到redis
        stringRedisTemplate
                .opsForValue()
                .set(RedisConstant.FILE_TASK + "_" + taskId, objectMapper.writeValueAsString(saveRedisData));

        // 添加上传块数列表（redis set为空时会被自动删除
        stringRedisTemplate
                .opsForSet()
                .add(RedisConstant.FILE_CHUNK_LIST + "_" + taskId, "");

        // 设置任务过期时间
        List.of(RedisConstant.FILE_CHUNK_LIST + "_" + taskId,
                        RedisConstant.FILE_TASK + "_" + taskId)
                .forEach(key -> stringRedisTemplate.expire(key,  RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS));
    }
}
