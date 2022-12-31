package io.github.franzli347.foss.utils;

import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.entity.MyVideo;
import io.github.franzli347.foss.exception.AsyncException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Async;
import ws.schild.jave.info.VideoInfo;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author AlanC
 * @Description 文件压缩工具类
 * @Date 17:28 2022/12/19
 * @return
 **/
@Slf4j
public class FileZipUtil {
    private FileZipUtil(){};
    /**
     * @return
     * @Author AlanC
     * @Description 文件压缩
     * @Date 18:09 2022/12/19
     * @Param [filepath]
     **/
    public static boolean videoCompress(String filepath, VideoCompressArgs compressArgs, ProcessInfo info, PropertyChangeListener listener) throws IOException {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || !Files.exists(p)|| Strings.isEmpty(filepath)||!FileUtil.isVideo(filepath)) {
            log.info("文件不存在或不为视频类型");
            throw new RuntimeException("文件不存在或不为视频类型");
        }
        return FfmpegUtil.videoCompress(filepath,compressArgs,info,listener);
    }


    public static boolean imageCompress(String filepath) throws IOException {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || !Files.exists(p)|| Strings.isEmpty(filepath)||!FileUtil.isPic(filepath)) {
            log.info("文件不存在或不为图片类型");
            throw new RuntimeException("文件不存在或不为视频类型");
        }
        return FfmpegUtil.imageCompress(filepath);
    }

}