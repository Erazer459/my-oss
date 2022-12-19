package io.github.franzli347.foss.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.support.fileSupport.*;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.FileZipUtil;
import io.github.franzli347.foss.utils.SnowflakeDistributeId;
import io.github.franzli347.foss.utils.asyncUtils.AsyncTaskManager;
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
import java.util.TimerTask;
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

    public FileUploadServiceImpl(SnowflakeDistributeId snowflakeDistributeId, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper, FilePathResolver filePathResolver, FileTransferResolver fileTransferResolver, ChunkPathResolver chunkPathResolver, FileUploadPostProcessorRegister fileUploadPostProcessorRegister) {
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
    public Result initMultipartUpload(FileUploadParam param) {
        // TODO : validate uid param
        // 查询文件是否存在
        //TODO bloom filter

        // 文件存在直接返回文件路径
        if (Optional
                .ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, param.getMd5()))
                .orElse(false)) {
            // TODO :返回文件路径
            return Result.builder()
                    .code(200)
                    .msg("file is exist")
                    .data(filePathResolver.getFilePath(param.getMd5()))
                    .build();
        }
        // 生成任务id
        param.setId(String.valueOf(snowflakeDistributeId.nextId()));
        param.setCreateTime(LocalDateTime.now());

        // 序列化任务到redis
        stringRedisTemplate
                .opsForValue()
                .set(RedisConstant.FILE_TASK + "_" + param.getId(), objectMapper.writeValueAsString(param));

        // 添加上传块数列表（redis set为空时会被自动删除
        stringRedisTemplate
                .opsForSet()
                .add(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId(), "");
        // 设置任务过期时间

        List.of(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId(),
                RedisConstant.FILE_TASK + "_" + param.getId())
                .forEach(key -> stringRedisTemplate.expire(key,  RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS));

        // 返回任务id
        return Result.builder()
                .data(param.getId())
                .code(200)
                .build();
    }


    /**
     * 检查上传任务当前块数
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    public Result check(FileUploadParam param) {
        // 查询任务是否存在
        objectMapper.readValue(Optional
                        .ofNullable(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + param.getId()))
                        .orElseThrow(() -> new RuntimeException("task is not exist"))
                        ,FileUploadParam.class);
        // 如果存在任务，返回已上传分片,不存在直接抛出异常
        return Result.builder()
                .data(Objects.requireNonNull(stringRedisTemplate.opsForSet().members(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId()))
                                .stream().filter(s -> !s.isBlank())
                                .collect(Collectors.toSet()))
                .code(200)
                .build();
    }

    /**
     * 分块上传
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Result uploadChunk(FileUploadParam param) {
        String id = Optional.ofNullable(param.getId()).orElseThrow(() -> new RuntimeException("id is null"));
        // 检查任务id
        objectMapper
                .readValue(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + param.getId()),FileUploadParam.class);
        // 获取当前上传分块
        int chunk = param.getChunk();
        String fileName = param.getName();
        // 异步保存文件分块到本地
        MultipartFile file = param.getFile();
        // 转存分片
        fileTransferResolver.transferFile(file, filePath + id + "." + chunk + ".chunk");
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + id, String.valueOf(chunk));
        Long upLoadChunks = stringRedisTemplate.opsForSet().size(RedisConstant.FILE_CHUNK_LIST + "_" + id);
        upLoadChunks = Optional.ofNullable(upLoadChunks).orElse(0L);
        //所有块都上传完成(+1是因为set中有一个空字符串)
        if (upLoadChunks == param.getChunks() + 1) {
            //异步merge文件
            // 异步删除redis信息
            AsyncTaskManager.me().execute(new TimerTask() {
                @Override
                public void run() {
                    stringRedisTemplate.delete(List.of(RedisConstant.FILE_CHUNK_LIST + "_" + id, id));
                    //  添加  md5 信息到redis
                    stringRedisTemplate.opsForSet().add(RedisConstant.FILE_MD5_LIST, param.getMd5());
                }
            });
            ///TODO :修改寻块逻辑(没想到啥好方法，先拼接字符串写着,后面直接注入新的ChunkPathResolver代替即可)
            // fix : 更改合并逻辑到主线程，防止合并失败无法返回错误信息
            String resultPath = filePath + fileName;
            List<String> collect = chunkPathResolver.getChunkPaths(id, param.getChunks());
            boolean merge = FileUtil.mergeFiles(collect.toArray(new String[0]), resultPath);
            if (!merge) {
                throw new RuntimeException("merge file error");
            }
            boolean compress= FileZipUtil.compress(resultPath);
            if(!compress){
                throw new RuntimeException("compress file error");
            }
            // 保存文件信息
            List<FileUploadPostProcessor> fileUploadPostProcessors = fileUploadPostProcessorRegister.getFileUploadPostProcessors();
            for (FileUploadPostProcessor fileUploadPostProcessor : fileUploadPostProcessors) {
                fileUploadPostProcessor.process(resultPath, param);
            }
            //返回信息
            return Result.builder()
                    .code(200)
                    //TODO:修改为http路径
                    .data(resultPath)
                    .msg("upload complete")
                    .build();
        }
        return Result.builder()
                .code(200)
                .msg("upload " + param.getName() + " chunk " + chunk + " success")
                .build();
    }
}
