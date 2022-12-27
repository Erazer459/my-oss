package io.github.franzli347.foss.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.dto.FileUploadParam;
import io.github.franzli347.foss.exception.FileException;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.support.fileSupport.*;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.SnowflakeDistributeId;
import io.github.franzli347.foss.utils.asyncUtils.AsyncTaskManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文件上传处理service
 *
 * @author FranzLi
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final SnowflakeDistributeId snowflakeDistributeId;

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

    public FileUploadServiceImpl(SnowflakeDistributeId snowflakeDistributeId,
                                 StringRedisTemplate stringRedisTemplate,
                                 ObjectMapper objectMapper,
                                 FilePathResolver filePathResolver,
                                 FileTransferResolver fileTransferResolver,
                                 ChunkPathResolver chunkPathResolver,
                                 FileUploadPostProcessorRegister fileUploadPostProcessorRegister) {
        this.snowflakeDistributeId = snowflakeDistributeId;
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
    public String initMultipartUpload(int uid,
                                      int bid,
                                      String name,
                                      int chunks,
                                      String md5,
                                      long size) {
        // 文件存在直接返回文件路径
        if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, md5)).orElse(false)) {
            return filePathResolver.getFilePath(md5);
        }
        // 生成任务id
        String taskId = String.valueOf(snowflakeDistributeId.nextId());

        FileUploadParam saveRedisData = new FileUploadParam(uid,
                taskId,
                chunks,
                size,
                bid,
                name,
                md5,
                LocalDateTime.now());

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
        objectMapper.readValue(Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + id))
                        .orElseThrow(() -> new RuntimeException("task_is_not_exist")),FileUploadParam.class);
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
    public String uploadChunk(String id,
                              int uid,
                              int bid,
                              String name,
                              int chunks,
                              int chunk,
                              Long size,
                              String md5,
                              MultipartFile file) {

        // 检查任务id
        objectMapper.readValue(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + id),FileUploadParam.class);
        // 转存分片 ( 最后path = filepath\tmp\12345.1.chunk
        fileTransferResolver.transferFile(file, "%stmp\\%s.%d.chunk".formatted(filePath,id,chunk));
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + id, String.valueOf(chunk));
        Long upLoadChunks = Optional.ofNullable(stringRedisTemplate.opsForSet().size(RedisConstant.FILE_CHUNK_LIST + "_" + id))
                            .orElse(0L);
        //所有块都上传完成(+1是因为set中有一个空字符串)

        if (upLoadChunks == chunks + 1) {
            // 异步删除redis信息
            AsyncTaskManager.me().execute(new TimerTask() {
                @Override
                public void run() {
                    stringRedisTemplate.delete(List.of(RedisConstant.FILE_CHUNK_LIST + "_" + id, id));
                    //  添加  md5 信息到redis
                    stringRedisTemplate.opsForSet().add(RedisConstant.FILE_MD5_LIST, md5);
                }
            });

            String resultPath = "%s%d\\%s".formatted(filePath, bid, name);
            List<String> collect = chunkPathResolver.getChunkPaths(id, chunks);

            boolean merge = FileUtil.mergeFiles(collect.toArray(new String[0]), resultPath);
            if (!merge) {
                throw new FileException("merge_file_error");
            }

            // 保存文件信息

            FileUploadParam saveData = new FileUploadParam(uid,
                    id,
                    chunks,
                    size,
                    bid,
                    name,
                    md5,
                    LocalDateTime.now());

            List<FileUploadPostProcessor> fileUploadPostProcessors = fileUploadPostProcessorRegister.getFileUploadPostProcessors();
            for (FileUploadPostProcessor fileUploadPostProcessor : fileUploadPostProcessors) {
                boolean processResult = fileUploadPostProcessor.process(resultPath, saveData);
                if(!processResult) {
                    throw new FileException("process_error");
                }
            }
            //返回信息
            return "%s/%d/%s".formatted(downloadAddr,bid,name);
        }
        return "upload[%s]chunk[%d]success".formatted(name,chunk);
    }
}
