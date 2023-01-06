package io.github.franzli347.foss.service.impl;

import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.exception.AsyncException;
import io.github.franzli347.foss.handler.WebSocketHandler;
import io.github.franzli347.foss.service.FileZipService;
import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.FileZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FileZipServiceImpl
 * @Author AlanC
 * @Date 2022/12/28 14:37
 **/
@Service
@Slf4j
public class FileZipServiceImpl implements FileZipService {//TODO 大文件压缩后修改文件md5
    FilesService filesService;
    private final WebSocketHandler webSocketHandler;
    private final StringRedisTemplate stringRedisTemplate;

    public FileZipServiceImpl(WebSocketHandler webSocketHandler, StringRedisTemplate stringRedisTemplate) {
        this.webSocketHandler = webSocketHandler;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    @Async
    public void videoCompress(int vid, VideoCompressArgs args, String userId) {
        if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.COMPRESS_TASK + "_" + vid, args.toString()))
                .orElse(false)) {//若压缩任务已存在则false
            throw new AsyncException( Result.builder().code(500).msg("压缩任务已存在").build(),userId);
        }
        Files files = filesService.getById(vid);
        //校验视频压缩参数:码率,分辨率,帧率
        List<String> error = FileUtil.compressArgsLegal(files.getPath(), args);
        if (!error.isEmpty()){
            throw new AsyncException( Result.builder().code(500).msg("视频压缩参数错误").data(error).build(),userId);}
        String key = RedisConstant.COMPRESS_TASK + "_" + vid;
        stringRedisTemplate.opsForSet().add(key, args.toString());
        stringRedisTemplate.expire(key, RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
        ProcessInfo info = ProcessInfo.builder().id(vid).build();
        try {
            FileZipUtil.videoCompress(files.getPath(), args, info, evt -> {//持续监听压缩进度
                if (evt.getPropertyName().equals("percentage") &&evt.getNewValue()!=evt.getOldValue()){
                    webSocketHandler.sendPercentageMsg(userId, (ProcessInfo) evt.getSource());
                }
                if (evt.getPropertyName().equals("done") && (boolean) evt.getNewValue()){
                    webSocketHandler.sendResultMsg(userId,Result.builder().msg("视频压缩完毕").code(200).data(evt.getSource()).build());
                }
            });
        } catch (Exception e) {
            stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + info.getId(), args.toString());
            throw new AsyncException(Result.builder().code(500).msg("视频压缩失败").data(vid).build(), userId);
        }
        stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + info.getId(), args);
    }

    @Override
    @Async
    public void imageCompress(int imageId, String userId) {
        String imagePath= String.valueOf(filesService.getById(imageId));
            if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId))
                    .orElse(false)) {
                throw new AsyncException(Result.builder().code(500).msg("压缩任务已存在").data(imageId).build(),userId);
            }
            if (new File(imagePath).length() / (1024 * 1024) > 600) {//限定图片大小
                throw new AsyncException(Result.builder().code(500).msg("图片大小不得超过600MB").data(imageId).build(),userId);
            }
        try {
            String key = RedisConstant.COMPRESS_TASK + "_" + imageId;
            stringRedisTemplate.opsForSet().add(key, String.valueOf(imageId));
            stringRedisTemplate.expire(key, RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
            FileZipUtil.imageCompress(imagePath);
        } catch (Exception e) {
            stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId);
            throw new AsyncException(Result.builder().code(500).msg("图片压缩失败").data(imageId).build(),String.valueOf(userId));
        }
        webSocketHandler.sendResultMsg(userId, Result.builder().code(200).msg("图片压缩成功").data(imageId).build());
        stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId);
    }
}
