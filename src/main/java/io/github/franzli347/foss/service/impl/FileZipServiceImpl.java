package io.github.franzli347.foss.service.impl;

import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.handler.WebSocketHandler;
import io.github.franzli347.foss.service.FileZipService;
import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.utils.FileUtil;
import io.github.franzli347.foss.utils.FileZipUtil;
import io.github.franzli347.foss.utils.asyncUtils.AsyncTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @ClassName FileZipServiceImpl
 * @Author AlanC
 * @Date 2022/12/28 14:37
 **/
@Service
@Slf4j
public class FileZipServiceImpl implements FileZipService {
    FilesService filesService;
    private static WebSocketHandler webSocketHandler;
    private final StringRedisTemplate stringRedisTemplate;


    public FileZipServiceImpl(WebSocketHandler webSocketHandler, StringRedisTemplate stringRedisTemplate) {
        this.webSocketHandler = webSocketHandler;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result videoCompress(int vid, VideoCompressArgs args) {
        if(Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(RedisConstant.COMPRESS_TASK+"_"+vid,args.toString()))
                .orElse(false)){//若压缩任务已存在则false
            return Result.builder().code(500).msg("压缩任务已存在").build();
        }
        Files files=filesService.getById(vid);
        //校验视频压缩参数:码率,分辨率,帧率
        List<String> error=FileUtil.compressArgsLegal(files.getPath(),args);
        if (!error.isEmpty())
            return Result.builder().code(500).msg("视频压缩参数错误").data(error).build();
        AsyncTaskManager.me().execute(new TimerTask() {
            @Override
            public void run() {
                stringRedisTemplate.opsForSet().add(RedisConstant.COMPRESS_TASK+"_"+vid,args.toString());
                ProcessInfo info = ProcessInfo.builder().id(vid).build();
                FileZipUtil.videoCompress(files.getPath(), args, info, new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {//持续监听压缩进度
                        log.info("新的进度:{}",((ProcessInfo) evt.getSource()).getPercentage());
                        webSocketHandler.sendPercentageMsg("token", (ProcessInfo) evt.getSource());
                        if (((ProcessInfo) evt.getSource()).isDone())//删除redis任务
                            stringRedisTemplate.opsForSet().remove(RedisConstant.COMPRESS_TASK+"_"+info.getId(),args);
                    }
                });
            }
        });
        return Result.builder().code(200).build();
    }

    @Override
    @Async
    public Result imageCompress(List<Integer> iList) {
        List<String> imageList=iList.stream().map(id-> filesService.getById(id).getPath()).collect(Collectors.toList());
            imageList.stream().forEach(image->{
                AsyncTaskManager.me().execute(new TimerTask() {
                    @Override
                    public void run() {
                        ProcessInfo info = ProcessInfo.builder().id(iList.get(imageList.indexOf(image))).build();
                        try {
                            if (new File(image).length()/(1024*1024)>600){//限定图片大小
                                throw new RuntimeException("图片文件不得超过600MB");
                            }
                        }catch (RuntimeException e){
                            e.printStackTrace();
                        }
                        FileZipUtil.imageCompress(image,info,new PropertyChangeListener(){
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                webSocketHandler.sendPercentageMsg("token", (ProcessInfo) evt.getSource());
                            }
                        });
                    }
                });
            });
            return Result.builder().code(200).build();
    }
}
