package io.github.franzli347.foss.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.model.entity.Bucket;
import io.github.franzli347.foss.exception.BucketException;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.utils.StreamUtil;
import io.github.franzli347.foss.model.dto.FileUploadParam;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.exception.FileException;
import io.github.franzli347.foss.web.service.FileUploadService;
import io.github.franzli347.foss.web.service.FilesService;
import io.github.franzli347.foss.support.fileSupport.ChunkPathResolver;
import io.github.franzli347.foss.support.fileSupport.FileTransferResolver;
import io.github.franzli347.foss.support.fileSupport.FileUploadPostProcessor;
import io.github.franzli347.foss.support.fileSupport.FileUploadPostProcessorRegister;
import io.github.franzli347.foss.utils.FileUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
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

    private final FileTransferResolver fileTransferResolver;

    private final ChunkPathResolver chunkPathResolver;

    private final FileUploadPostProcessorRegister fileUploadPostProcessorRegister;

    private final FilesService filesService;

    private final BucketService bucketService;

    private final double EPSILON = 0.001;


    @Value("${pathMap.source}")
    private String filePath;

    @Value("${tmpFilePattern}")
    private String tmpFilePattern;

    public FileUploadServiceImpl(StringRedisTemplate stringRedisTemplate,
                                 ObjectMapper objectMapper,
                                 FileTransferResolver fileTransferResolver,
                                 ChunkPathResolver chunkPathResolver,
                                 FileUploadPostProcessorRegister fileUploadPostProcessorRegister,
                                 FilesService filesService, BucketService bucketService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.fileTransferResolver = fileTransferResolver;
        this.chunkPathResolver = chunkPathResolver;
        this.fileUploadPostProcessorRegister = fileUploadPostProcessorRegister;
        this.filesService = filesService;
        this.bucketService = bucketService;
    }

    /**
     * 创建上传任务
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    @Transactional
    public boolean initMultipartUpload(final int uid,
                                      final int bid,
                                      final String name,
                                      final int chunks,
                                      final String md5,
                                      final long size) {

        // 检查bucket容量大小
        Bucket bucket = bucketService.getById(bid);
        double freeSize = bucket.getTotalSize() - bucket.getUsedSize();
        if(freeSize < size){
            throw new BucketException("容量不足");
        }
        FileUploadParam dt = new FileUploadParam(uid,
                chunks,
                size,
                bid,
                name,
                md5,
                LocalDateTime.now());

        initUploadDataToRedis(dt);
        return true;
    }


    @Override
    @Transactional
    public boolean secUpload(final String md5,final String targetBid){
        boolean isUpload = Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, md5)).orElse(false);
        if(isUpload){
            saveFileDataToOther(md5,targetBid);
        }
        return isUpload;
    }

    @Override
    public boolean abort(String md5) {
        // 获取已经上传的分块
        Set<String> members = stringRedisTemplate.opsForSet().members(RedisConstant.FILE_CHUNK_LIST + "_" + md5);
        removeUploadDataFromRedis(md5);
        //获取已经上传分块的文件路径
        List<String> chunkPaths = chunkPathResolver.getChunkPaths(md5, members);
        //删除已经上传分块
        chunkPaths.forEach(StreamUtil.throwingConsumerWrapper(v -> java.nio.file.Files.deleteIfExists(Path.of(v))));
        return true;
    }

    @Override
    @Transactional
    public boolean smallFileUpload(int uid, int bid, String name, long size, String md5, MultipartFile file) {
        //判断文件是否已经上传
        boolean isUpload = Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.FILE_MD5_LIST, md5)).orElse(false);
        if (isUpload) {
            saveFileDataToOther(md5, String.valueOf(bid));
            return true;
        }
        String resultPath = getResultPath(bid,name);
        //转存文件

        fileTransferResolver.transferFile(file, resultPath);
        //保存文件md5到redis
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_MD5_LIST, md5);
        FileUploadParam dt = new FileUploadParam(uid, 0, size, bid, name, md5, LocalDateTime.now());
        doFilePostProcessor(resultPath,dt);
        return true;
    }





    /**
     * 检查上传任务当前块数
     *
     * @return Result
     */
    @Override
    @SneakyThrows
    public Set<String> check(String md5) {
        // 查询任务是否存在
        checkTaskStatus(md5);
        // 如果存在任务，返回已上传分片,不存在直接抛出异常
        return Objects.requireNonNull(stringRedisTemplate.opsForSet()
                        .members(RedisConstant.FILE_CHUNK_LIST + "_" + md5))
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
    public String uploadChunk(final int uid,
                              final int bid,
                              final String name,
                              final int chunks,
                              final int chunk,
                              final Long size,
                              final String md5,
                              final MultipartFile file) {
        // 检查任务id
        checkTaskStatus(md5);
        //检查分块是否曾经上传
        boolean chunkCheckError = Optional.ofNullable(stringRedisTemplate.opsForSet()
                                .isMember(RedisConstant.FILE_CHUNK_LIST + "_" + md5, String.valueOf(chunk)))
                                .orElseThrow(() -> new FileException("chunk_check_error"));
        if(chunkCheckError){
            return "upload[%s]chunk[%d]success".formatted(name,chunk);
        }
        // 转存分片 ( 最后path = filepath\tmp\12345.1.chunk
        fileTransferResolver.transferFile(file,tmpFilePattern.formatted(filePath,md5,chunk));
        // 添加上传块数列表
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_CHUNK_LIST + "_" + md5, String.valueOf(chunk));

        Long upLoadChunks = Optional.ofNullable(stringRedisTemplate.opsForSet().size(RedisConstant.FILE_CHUNK_LIST + "_" + md5))
                .orElseThrow(() -> new FileException("task_not_exist"));
        if (upLoadChunks == chunks) {
            return afterChunksUpload(uid, bid, name, chunks, size, md5);
        }
        return "upload[%s]chunk[%d]success".formatted(name,chunk);
    }


    private String afterChunksUpload(int uid, int bid, String name, int chunks, Long size, String md5) throws IOException {
        String resultPath = getResultPath(bid, name);
        List<String> collect = chunkPathResolver.getChunkPaths(md5, chunks);
        boolean merge = FileUtil.mergeFiles(collect.toArray(new String[0]), resultPath);
        if (!merge) {
            throw new FileException("merge_file_error");
        }
        removeUploadDataFromRedis(md5);
        //  添加 md5 信息到redis
        stringRedisTemplate.opsForSet().add(RedisConstant.FILE_MD5_LIST, md5);
        stringRedisTemplate.expire(RedisConstant.FILE_MD5_LIST + "_" + md5,RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);

        // 保存文件信息
        FileUploadParam saveData = new FileUploadParam(uid, chunks, size, bid, name, md5, LocalDateTime.now());
        doFilePostProcessor(resultPath, saveData);
        //返回信息
        return "Multipart_upload_complete";
    }

    private void removeUploadDataFromRedis(String md5) {
        // 删除redis中的任务信息
        stringRedisTemplate.delete(List.of(RedisConstant.FILE_CHUNK_LIST + "_" + md5, RedisConstant.FILE_TASK + "_" + md5));
    }

    private void doFilePostProcessor(String resultPath, FileUploadParam saveData) {
        List<FileUploadPostProcessor> fileUploadPostProcessors = fileUploadPostProcessorRegister.getFileUploadPostProcessors();
        for (FileUploadPostProcessor fileUploadPostProcessor : fileUploadPostProcessors) {
            boolean processResult = fileUploadPostProcessor.process(resultPath, saveData);
            if(!processResult) {
                throw new FileException("process_error on " + fileUploadPostProcessor.getClass().getName());
            }
        }
    }

    private void checkTaskStatus(String md5) throws JsonProcessingException {
        objectMapper.readValue(Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisConstant.FILE_TASK + "_" + md5))
                .orElseThrow(() -> new RuntimeException("task_is_not_exist")),FileUploadParam.class);
    }


    @SneakyThrows
    private void initUploadDataToRedis(FileUploadParam dt) {
        // 序列化任务到redis
        stringRedisTemplate
                .opsForValue()
                .set(RedisConstant.FILE_TASK + "_" + dt.getMd5(), objectMapper.writeValueAsString(dt));
        // 设置任务过期时间
        stringRedisTemplate.expire(RedisConstant.FILE_CHUNK_LIST + "_" + dt.getMd5(),RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
    }

    private String getResultPath(int bid, String name) {
        return "%s%d/%s".formatted(filePath, bid, name);
    }

    private void saveFileDataToOther(final String md5,final String targetBid){
        Files files = filesService.query().eq("md5", md5).oneOpt()
                .orElseThrow(() ->new FileException("saveFileDataToOtherError"));
        files.setBid(Integer.valueOf(targetBid));
        files.setId(IdUtil.getSnowflakeNextId());
        filesService.save(files);
    }

}
