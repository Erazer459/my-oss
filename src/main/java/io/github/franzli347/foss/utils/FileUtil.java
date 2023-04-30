package io.github.franzli347.foss.utils;

import io.github.franzli347.foss.model.dto.VideoCompressArgs;
import io.github.franzli347.foss.model.entity.MyVideo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author FranzLi
 */
@Slf4j
public class FileUtil {

    private FileUtil() {
    }
    public static String getExtraName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    public static String getName(String path){
        File file=new File(path);
        return file.getName();
    }
    public static String getNameWithoutExtra(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static boolean mergeFiles(String[] filePath, String resultPath) throws IOException {
        Path p = Path.of(resultPath);
        if (Files.isDirectory(p) || Files.exists(p) || filePath == null || filePath.length < 1 || Strings.isEmpty(resultPath)) {
            return false;
        }
        Path pwn = Path.of(getFilePathWithoutName(resultPath));
        // 文件夹不存在创建文件夹
        if(!Files.exists(pwn)){
            Files.createDirectories(pwn);
        }
        if (filePath.length == 1) {
            return new File(filePath[0]).renameTo(new File(resultPath));
        }
        File[] files = new File[filePath.length];
        for (int i = 0; i < filePath.length; i ++) {
            files[i] = new File(filePath[i]);
            if (Strings.isEmpty(filePath[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try(FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel()) {
            for (int i = 0; i < filePath.length; i ++) {
                try(FileChannel blk = new FileInputStream(files[i]).getChannel()){
                    resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (String s : filePath) {
            Files.delete(Path.of(s));
        }

        return true;
    }
    public static boolean isPic(String filePath){
        File file=new File(filePath);
        MimetypesFileTypeMap fileTypeMap=new MimetypesFileTypeMap();
        fileTypeMap.addMimeTypes("image png tif jpg jpeg bmp");
        String mimetype=fileTypeMap.getContentType(file);
        log.info("文件类型:{}",mimetype);
        return mimetype.contains("image");
    }
    public static boolean isVideo(String filePath){
        File file=new File(filePath);
        String specialType=FileUtil.getExtraName(filePath);
        if (specialType.equals(".mov")||specialType.equals(".mkv")){//特判两种特殊类型
            return true;
        }
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(file.getName());
        log.info("文件类型:{}",type);
        return type.contains("video");
    }
    @SneakyThrows
    public static List<String> compressArgsLegal(String videoPath,VideoCompressArgs args){
        MyVideo videoInfo=FfmpegUtil.getVideoInfo(videoPath);
        List<String> error=new ArrayList<>();
        if (args.getBitRate()>videoInfo.getBitRate()) {
            log.info("原视频码率:{}", videoInfo.getBitRate());
            error.add("码率不得超过原视频");
        }
        if (args.getFrameRate()>videoInfo.getFrameRate()&&videoInfo.getBitRate()!=0) {
            log.info("原视频帧率:{}", videoInfo.getFrameRate());
            error.add("帧率不得超过原视频");
        }
        if (args.getVideoSize().getWidth()>videoInfo.getVideoSize().getWidth()&&args.getVideoSize().getHeight()>videoInfo.getVideoSize().getHeight()){
            log.info("原视频分辨率:{}",videoInfo.getVideoSize());
            error.add("分辨率不得超过原视频");
        }
        return error;
    }

    public static String getFilePathWithoutName(String filePath){
        return filePath.substring(0, filePath.lastIndexOf("/"));/////
    }


}
