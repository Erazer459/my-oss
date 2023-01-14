package io.github.franzli347.foss.utils;

import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.dto.VideoCompressArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.beans.PropertyChangeListener;
import java.io.IOException;
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
    private FileZipUtil(){}
    /**
     * @return
     * @Author AlanC
     * @Description 文件压缩
     * @Date 18:09 2022/12/19
     * @Param [filepath]
     **/
    public static void videoCompress(String filepath, VideoCompressArgs compressArgs, ProcessInfo info, PropertyChangeListener listener) throws IOException {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || !Files.exists(p)|| Strings.isEmpty(filepath)||!FileUtil.isVideo(filepath)) {
            throw new IOException("文件不存在或不为视频类型");
        }
         FfmpegUtil.videoCompress(filepath,compressArgs,info,listener);
    }


    public static void imageCompress(String filepath, int quality) throws IOException {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || !Files.exists(p)|| Strings.isEmpty(filepath)||!FileUtil.isPic(filepath)) {
            throw new IOException("文件不存在或不为图片类型");
        }
         FfmpegUtil.imageCompress(filepath,quality);
    }

}