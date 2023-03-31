package io.github.franzli347.foss.utils;

import io.github.franzli347.foss.model.vo.ProcessInfo;
import io.github.franzli347.foss.model.dto.VideoCompressArgs;
import io.github.franzli347.foss.model.entity.MyVideo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoInfo;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;


/**
 * 核心工具包
 *
 * @author shuishan
 * @since 2021/7/22
 */
@Slf4j
public class FfmpegUtil {

    private  FfmpegUtil() {
    }

    /**
     * 获取音视频时长
     * @param sourcePath 音视频路径
     */
    public static long getFileDuration(String sourcePath) throws EncoderException {
        MultimediaObject multimediaObject = new MultimediaObject(new File(sourcePath));
        MultimediaInfo multimediaInfo = multimediaObject.getInfo();
        return multimediaInfo.getDuration();
    }


    /**
     * 剪切音视频
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @param offetTime   起始时间，格式 00:00:00.000   小时:分:秒.毫秒
     * @param endTime   同上
     */
    public static void cutAv(String sourcePath, String targetPath, String offetTime, String endTime) {
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-ss");
            ffmpeg.addArgument(offetTime);
            ffmpeg.addArgument("-t");
            ffmpeg.addArgument(endTime);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(sourcePath);
            ffmpeg.addArgument("-vcodec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-acodec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument(targetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("切除视频成功={}", targetPath);
        } catch (IOException e) {
            throw new RuntimeException("剪切视频失败", e);
        }
    }

    /**
     * 等待命令执行成功，退出
     *
     * @param br
     * @throws IOException
     */
    private static void blockFfmpeg(BufferedReader br) throws IOException {
        String line;
        // 该方法阻塞线程，直至合成成功
        while ((line = br.readLine()) != null) {
            doNothing(line);
        }
        br.close();
    }
    @SneakyThrows
    private static void blockFfmpeg(BufferedReader br, ProcessInfo info, PropertyChangeListener listener)throws IOException{
        String line;
        while((line = br.readLine()) != null){
            String line2=StringUtils.deleteWhitespace(line.toLowerCase());
            long source;
            if (line2.contains("duration:") && line2.contains("bitrate:") && line2.contains("start:")){//获取原视频长度
                source= Long.parseLong(line2.substring(line2.indexOf("duration:")+StringUtils.length("duration:"),line2.indexOf(",start")).replace(":","").replace(".",""));
                info.setSource(source);
            }
            if (line2.contains("frame=")&&line2.contains("time=")&&line2.contains("size=")){//开始读取视频处理进度
                Thread.sleep(1500);
                changePercentage(info,line2,listener);
            }else{
                doNothing(line);//没有视频信息或进度信息则等待
            }
        }
        br.close();
        info.setDone(true);//任务完成
        listener.propertyChange(new PropertyChangeEvent(info,"done",false,true));//通知
    }
    private static void changePercentage(ProcessInfo info, String line,PropertyChangeListener listener){
        Double oldPercentage=info.getPercentage();
        long timeProcessed = Long.parseLong(line.substring(line.indexOf("time=")+ StringUtils.length("time="), line.indexOf("bitrate")).replace(":","").replace(".",""));
        info.setPercentage(new BigDecimal(timeProcessed*1.0 / info.getSource() * 100).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        listener.propertyChange(new PropertyChangeEvent(info,"percentage",oldPercentage,info.getPercentage()));
    }
    /**
     * 打印日志，调试阶段可解开注释，观察执行情况
     *
     * @param line
     */
    private static void doNothing(String line) {
    log.info(line);
    }


    /**
     * 合并两个视频
     *
     * @param firstFragmentPath   资源本地路径或者url
     * @param secondFragmentPath  资源本地路径或者url**
     * @param targetPath     目标存储位置
     * @throws Exception
     */
    public static void mergeAv(String firstFragmentPath, String secondFragmentPath,
                               String targetPath) {
        try {
            log.info("合并视频处理中firstFragmentPath={},secondFragmentPath={},请稍后.....", firstFragmentPath,
                    secondFragmentPath);
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(firstFragmentPath);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(secondFragmentPath);
            ffmpeg.addArgument("-filter_complex");
            ffmpeg.addArgument(
                    "\"[0:v] [0:a] [1:v] [1:a] concat=n=2:v=1:a=1 [v] [a]\" -map \"[v]\" -map \"[a]\"");
            ffmpeg.addArgument(targetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("合并视频成功={}", targetPath);
        } catch (IOException e) {
            throw new RuntimeException("合并视频失败", e);
        }
    }


    /**
     * 获取视频原声
     *
     * @param sourcePath  本地路径或者url
     * @param targetPath  本地存储路径
     */
    public static String getAudio(String sourcePath, String targetPath, String taskId) {
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(sourcePath);
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("mp3");
            ffmpeg.addArgument("-vn");
            ffmpeg.addArgument(targetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("获取视频音频={}", targetPath);
        } catch (IOException e) {
            throw new RuntimeException("获取视频音频失败", e);
        }
        return targetPath;
    }

    /**
     * 合并音频
     *
     * @param originAudioPath  音频url或本地路径
     * @param magicAudioPath  音频url或本地路径
     * @param audioTargetPath  目标存储本地路径
     */
    public static void megerAudioAudio(String originAudioPath, String magicAudioPath,
                                       String audioTargetPath, String taskId) {
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(originAudioPath);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(magicAudioPath);
            ffmpeg.addArgument("-filter_complex");
            ffmpeg.addArgument("amix=inputs=2:duration=first:dropout_transition=2");
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("mp3");
            ffmpeg.addArgument("-y");
            ffmpeg.addArgument(audioTargetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("合并音频={}", audioTargetPath);
        } catch (IOException e) {
            throw new RuntimeException("合并音频失败", e);
        }
    }



    /**
     * 视频加声音
     *
     * @param videoPath   视频
     * @param megerAudioPath  音频
     * @param videoTargetPath 目标地址
     * @param taskId  可忽略，自行删除taskid
     * @throws Exception
     */
    public static void mergeVideoAndAudio(String videoPath, String megerAudioPath,
                                          String videoTargetPath, String taskId) {
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(videoPath);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(megerAudioPath);
            ffmpeg.addArgument("-codec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-shortest");
            ffmpeg.addArgument(videoTargetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("获取视频(去除音频)={}", videoTargetPath);
        } catch (IOException e) {
            throw new RuntimeException("获取视频(去除音频)失败", e);
        }
    }

    /**
     * 视频增加字幕
     *
     * @param videoPath
     * @param sutitleVideoSavePath
     * @param wordPath  固定格式的srt文件地址或存储位置，百度即可
     * @return
     * @throws Exception
     */
    public static boolean addSubtitle(String videoPath, String sutitleVideoSavePath,
                                      String wordPath, String taskId) {
        try {
            log.info("开始合成字幕....");
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(videoPath);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(wordPath);
            ffmpeg.addArgument("-c");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument(sutitleVideoSavePath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("添加字幕成功={}", videoPath);
        } catch (IOException e) {
            throw new RuntimeException("添加字幕失败", e);
        }
        return true;
    }

    /**
     * 图片生成视频   帧率设置25，可自行修改
     *
     * @param videoPath
     * @param videoPath
     * @return
     * @throws Exception
     */
    public static boolean picToVideo(String picsPath, String videoPath, String taskId) {
        try {
            log.info("图片转视频中....");
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(picsPath);
            ffmpeg.addArgument("-r");
            ffmpeg.addArgument("25");
            ffmpeg.addArgument("-y");
            ffmpeg.addArgument(videoPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            log.info("图片转视频成功={}", videoPath);
        } catch (IOException e) {
            log.error("图片转视频失败={}", e.getMessage());
            throw new RuntimeException("图片转视频失败", e);
        }
        return true;
    }


    /**
     * 获取视频信息
     *
     * @param url
     * @return
     */
    public static MultimediaInfo getVideoInfo(URL url) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(url);
            return multimediaObject.getInfo();
        } catch (EncoderException e) {
            log.error("获取视频信息报错={}", e.getMessage());
            throw new RuntimeException("获取视频信息报错", e);
        }
    }
    /**
     * @Author AlanC
     * @Description  通过路径获取视频信息
     * @Date 15:09 2022/12/26
     * @Param [videoPath]
     * @return VideoInfo
     **/
    public static MyVideo getVideoInfo(String videoPath) throws EncoderException {
        File file=new File(videoPath);
        MultimediaObject multimediaObject = new MultimediaObject(file);
        VideoInfo vInfo=multimediaObject.getInfo().getVideo();
        AudioInfo aInfo=multimediaObject.getInfo().getAudio();
        return MyVideo.builder()
                .videoLength(multimediaObject.getInfo().getDuration()/1000)
                .bitRate(vInfo.getBitRate())
                .channels(aInfo.getChannels())
                .frameRate(vInfo.getFrameRate())
                .format(multimediaObject.getInfo().getFormat())
                .videoSize(vInfo.getSize())
                .sampleRate(aInfo.getSamplingRate())
                .build();
    }
    /**
     * @Author AlanC
     * @Description 视频压缩
     * @Date 22:20 2022/12/25
     * @Param [videoPath]
     * @return
     **/
    public static void videoCompress(String videoPath, VideoCompressArgs compressArgs, ProcessInfo info, PropertyChangeListener listener) throws IOException {
        File oldFile=new File(videoPath);
        String tempPath=oldFile.getParent()+File.separator+System.currentTimeMillis()+oldFile.getName();
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(videoPath);
            ffmpeg.addArgument("-c:v");
            ffmpeg.addArgument("libx264");//编码格式H.264
            ffmpeg.addArgument("-preset");
            ffmpeg.addArgument("veryslow");
            ffmpeg.addArgument("-crf");
            ffmpeg.addArgument("20");//crf参数
            ffmpeg.addArgument("-c:a");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-b:v");
            ffmpeg.addArgument(String.valueOf(compressArgs.getBitRate()));//比特率
            ffmpeg.addArgument("-s");
            ffmpeg.addArgument(compressArgs.getVideoSizeAsFFArg());//分辨率
            ffmpeg.addArgument("-r");
            ffmpeg.addArgument(String.valueOf(compressArgs.getFrameRate()));//帧数
            ffmpeg.addArgument(tempPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br,info,listener);
            }
            Files.delete(oldFile.toPath());
            File result=new File(tempPath);
            result.renameTo(oldFile);
            ffmpeg.close();
    }
    /**
     * @Author AlanC
     * @Description  ffmpeg图片压缩
     * @Date 17:17 2022/12/26
     * @Param [imagePath]
     * @return
     **/
    public static void imageCompress(String imagePath, int quality) throws IOException {
        File oldFile=new File(imagePath);
        String tempPath=oldFile.getParent()+File.separator+System.currentTimeMillis()+oldFile.getName();
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument(imagePath);
        ffmpeg.addArgument("-vf");
        ffmpeg.addArgument("scale=iw:ih");
        ffmpeg.addArgument("-codec");
        ffmpeg.addArgument("libwebp");
        ffmpeg.addArgument("-lossless");
        if (FileUtil.getExtraName(imagePath).equals(".png")){//仅对png使用无损压缩,提升质量和压缩效率
                ffmpeg.addArgument("1");
                ffmpeg.addArgument("-q");
                ffmpeg.addArgument("100");
        }else {
            ffmpeg.addArgument("0");
            ffmpeg.addArgument("-q");
            ffmpeg.addArgument(String.valueOf(quality));
        }
        ffmpeg.addArgument(tempPath);
        ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
            Files.delete(oldFile.toPath());
            File result=new File(tempPath);
            result.renameTo(oldFile);
            ffmpeg.close();
    }
}

