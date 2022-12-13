package io.github.franzli347.toss.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.toss.common.FileUploadParam;
import io.github.franzli347.toss.common.RedisConstant;
import io.github.franzli347.toss.common.Result;
import io.github.franzli347.toss.service.FileUploadService;
import io.github.franzli347.toss.utils.SnowflakeDistributeId;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final SnowflakeDistributeId snowflakeDistributeId;

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    public FileUploadServiceImpl(SnowflakeDistributeId snowflakeDistributeId, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.snowflakeDistributeId = snowflakeDistributeId;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }
    /**
     * 创建上传任务
     * @param param
     * @return Result
     */
    @Override
    @SneakyThrows
    public Result initMultipartUpload(FileUploadParam param) {
        // 查询文件是否存在
        //TODO bloom filter
        Boolean member = stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, param.getMd5());
        // 文件存在直接返回文件路劲
        if (Optional.ofNullable(member).orElse(false)) {
            // TODO :返回文件路径
            return Result.builder().code(200).msg("file is upload").data("path").build();
        }
        // 生成任务id
        param.setId(String.valueOf(snowflakeDistributeId.nextId()));
        // 序列化任务到redis
        stringRedisTemplate.opsForValue().set(param.getId(), objectMapper.writeValueAsString(param));
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId(),"" );
        // 返回任务id
        return Result.builder().data(param.getId()).code(200).build();
    }


    /**
     * 检查上传任务当前块数
     * @param param
     * @return Result
     */
    @Override
    @SneakyThrows
    public Result check(FileUploadParam param) {
        // 查询任务是否存在
        FileUploadParam uploadParam = objectMapper.readValue(stringRedisTemplate.opsForValue().get(param.getId()), FileUploadParam.class);
        // 如果存在任务，返回已上传分片数量
        if (Optional.ofNullable(uploadParam).isPresent()) {
            return Result.builder().code(200).msg("continue").data(uploadParam).build();
        }
        // 任务不存在,返回失败
        return Result.builder().code(500).msg("check status fail").build();
    }

    /**
     * 分块上传
     * @param param
     * @return Result
     */
    @Override
    @SneakyThrows
    public Result uploadChunk(FileUploadParam param) {
        FileUploadParam uploadParam = objectMapper.readValue(stringRedisTemplate.opsForValue().get(param.getId()), FileUploadParam.class);
        // 判断任务是否存在，不存在直接抛出异常
        Optional.ofNullable(param).orElseThrow(() -> new RuntimeException("task is not exist"));
        // 获取当前上传分块
        int chunk = param.getChunk();
        // 异步保存文件分块到本地
        MultipartFile file = param.getFile();
        file.transferTo(Path.of("E:\\ceodes\\f-oss\\src\\main\\resources\\fileStore" + file.getOriginalFilename() + chunk));
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + param.getId(), String.valueOf(chunk));
        return Result.builder().code(200).msg("upload " + file.getName() + " chunk" +chunk + " success").build();
    }
}
