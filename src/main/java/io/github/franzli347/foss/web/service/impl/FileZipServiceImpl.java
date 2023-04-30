package io.github.franzli347.foss.web.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.ObjectMapper;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.common.constant.ResultCode;
import io.github.franzli347.foss.common.constant.WsTagConstant;
import io.github.franzli347.foss.model.dto.VideoCompressArgs;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.exception.AsyncException;
import io.github.franzli347.foss.middleware.handler.WebSocketHandler;
import io.github.franzli347.foss.model.vo.ProcessInfo;
import io.github.franzli347.foss.model.vo.WsResult;
import io.github.franzli347.foss.web.service.FileZipService;
import io.github.franzli347.foss.web.service.FilesService;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.FileZipUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    static final String PERCENTAGE="percentage";
    static final String DONE="done";
    @Value("${pathMap.source}")
    String filePath;
    private final FilesService filesService;
    private final WebSocketHandler webSocketHandler;
    private final StringRedisTemplate stringRedisTemplate;

    public FileZipServiceImpl(FilesService filesService, WebSocketHandler webSocketHandler, StringRedisTemplate stringRedisTemplate) {
        this.filesService = filesService;
        this.webSocketHandler = webSocketHandler;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @SneakyThrows
    @Override
    @Async
    public void videoCompress(String vid, VideoCompressArgs args, String userId) {
        if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.COMPRESS_TASK + "_" + vid, args.toString()))
                .orElse(false)) {//若压缩任务已存在则false
            throw new AsyncException( WsResult.builder().code(ResultCode.CODE_ERROR).wsTag(WsTagConstant.COMPRESS).msg("压缩任务已存在").userId(userId).build());
        }
        Files files = filesService.getById(vid);
        //校验视频压缩参数:码率,分辨率,帧率
        List<String> error = FileUtil.compressArgsLegal(filePath+files.getPath(), args);
        if (!error.isEmpty()){
            throw new AsyncException( WsResult.builder().code(ResultCode.CODE_ERROR).msg("视频压缩参数错误").data(error).userId(userId).build());}
        String key = RedisConstant.COMPRESS_TASK + "_" + vid;
        stringRedisTemplate.opsForSet().add(key, WsTagConstant.COMPRESS);
        stringRedisTemplate.expire(key, RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
        ProcessInfo info = ProcessInfo.builder().id(vid).build();
        try {
            FileZipUtil.videoCompress(filePath+files.getPath(), args, info, evt -> {//持续监听压缩进度
                if (evt.getPropertyName().equals(PERCENTAGE) &&evt.getNewValue()!=evt.getOldValue()){
                    webSocketHandler.sendResultMsg(WsResult.builder().wsTag(WsTagConstant.COMPRESS).userId(userId).data(evt.getSource()).build());
                }
                if (evt.getPropertyName().equals(DONE) && (boolean) evt.getNewValue()){
                    webSocketHandler.sendResultMsg(WsResult.builder().msg("视频压缩完毕").wsTag(WsTagConstant.COMPRESS).code(ResultCode.CODE_SUCCESS).data(evt.getSource()).userId(userId).build());
                }
            });
        } catch (Exception e) {
            stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + info.getId(), true);
            throw new AsyncException(WsResult.builder().code(ResultCode.CODE_ERROR).wsTag(WsTagConstant.COMPRESS).msg("视频压缩失败").data(vid).userId(userId).build());
        }
        stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + info.getId(), WsTagConstant.COMPRESS);
    }

    @SneakyThrows
    @Override
    @Async
    public void imageCompress(String imageId,int quality,String userId) {
        String imagePath= filePath+filesService.getById(imageId).getPath();
            if (Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId))
                    .orElse(false)) {
                throw new AsyncException(WsResult.builder().code(ResultCode.CODE_ERROR).wsTag(WsTagConstant.COMPRESS).msg("压缩任务已存在").data(imageId).userId(userId).build());
            }
            if (new File(imagePath).length() / (1024 * 1024) > 600) {//限定图片大小
                throw new AsyncException(WsResult.builder().code(ResultCode.CODE_ERROR).wsTag(WsTagConstant.COMPRESS).msg("图片大小不得超过600MB").data(imageId).userId(userId).build());
            }
        try {
            String key = RedisConstant.COMPRESS_TASK + "_" + imageId;
            stringRedisTemplate.opsForSet().add(key,imageId);
            stringRedisTemplate.expire(key, RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
            FileZipUtil.imageCompress(imagePath,quality);
        } catch (Exception e) {
            stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId);
            throw new AsyncException(WsResult.builder().code(ResultCode.CODE_ERROR).wsTag(WsTagConstant.COMPRESS).msg("图片压缩失败").data(imageId).userId(userId).build());
        }
        stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK + "_" + imageId, imageId);
        webSocketHandler.sendResultMsg(WsResult.builder().code(ResultCode.CODE_SUCCESS).wsTag(WsTagConstant.COMPRESS).msg("图片压缩成功").data(imageId).userId(userId).build());
    }
}
