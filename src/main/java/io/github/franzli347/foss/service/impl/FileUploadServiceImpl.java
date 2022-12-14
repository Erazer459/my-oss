package io.github.franzli347.foss.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.support.FilePathResolver;
import io.github.franzli347.foss.utils.asyncUtils.AsyncTaskManager;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.SnowflakeDistributeId;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final SnowflakeDistributeId snowflakeDistributeId;

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    private final FilePathResolver filePathResolver;

    private final String filePath = "E:\\ceodes\\f-oss\\src\\main\\resources\\fileStore\\";


    public FileUploadServiceImpl(SnowflakeDistributeId snowflakeDistributeId, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper, FilePathResolver filePathResolver) {
        this.snowflakeDistributeId = snowflakeDistributeId;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.filePathResolver = filePathResolver;
    }
    /**
     * 创建上传任务
     * @param param
     * @return Result
     */
    @Override
    @SneakyThrows
    public Result initMultipartUpload(FileUploadParam param) {
        // TODO : validate uid param
        // 查询文件是否存在
        //TODO bloom filter
        Boolean member = stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, param.getMd5());
        // 文件存在直接返回文件路径
        if (Optional.ofNullable(member).orElse(false)) {
            // TODO :返回文件路径
            String path = filePathResolver.getFilePath(param.getMd5());
            return Result.builder().code(200).msg("file is exist").data(path).build();
        }
        // 生成任务id
        param.setId(String.valueOf(snowflakeDistributeId.nextId()));
        // 序列化任务到redis
        stringRedisTemplate.opsForValue().set(RedisConstant.FILE_TASK+ "_" +param.getId(), objectMapper.writeValueAsString(param));
        // 添加上传块数列表（redis set为空时会被自动删除
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
        FileUploadParam uploadParam = objectMapper.readValue(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK+ "_" +param.getId()), FileUploadParam.class);
        // 如果存在任务，返回已上传分片,不存在直接抛出异常
        Set<String> members = Objects.requireNonNull(stringRedisTemplate.
                        opsForSet().
                        members(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId())).
                        stream().
                // 过滤空字符串
                        filter(s -> !s.isBlank()).
                        collect(Collectors.toSet());
        return Result.builder().data(members).code(200).build();
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
        String fileName = param.getName();
        // 异步保存文件分块到本地
        MultipartFile file = param.getFile();
        // TODO: CP
        file.transferTo(
                Path.of(filePath
                        + FileUtil.getNameWithoutExtra(fileName) + "."
                        + chunk + ".chunk")
        );
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId(),String.valueOf(chunk));
        Long size = stringRedisTemplate.opsForSet().size(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId());
        size = Optional.ofNullable(size).orElse(0L);
        //所有块都上传完成(+1是因为set中有一个空字符串)
        if(size == param.getChunks() + 1){
            // 异步删除redis信息
            AsyncTaskManager.me().execute(
                    new TimerTask() {
                        @Override
                        public void run() {
                            stringRedisTemplate.delete(RedisConstant.FILE_CHUNK_LIST + "_" + param.getId());
                            stringRedisTemplate.delete(param.getId());
                        }
                    }
            );
            //异步merge文件
            AsyncTaskManager.me().execute(new TimerTask() {
                @Override
                @SneakyThrows
                public void run() {
                    try(Stream<Path> walk = Files.walk(Path.of(filePath),1)){
                        List<String> collect = walk.
                                map(Path::toString).
                                filter(p ->
                                        p.contains(FileUtil.getNameWithoutExtra(fileName))
                                ).toList();
                        FileUtil.mergeFiles(collect.toArray(new String[0]), filePath + "test.txt");
                    }
                }
            });
            //保存文件信息
            //返回信息
        }
        return Result.builder().code(200).msg("upload " + file.getName() + " chunk" +chunk + " success").build();
    }
}
