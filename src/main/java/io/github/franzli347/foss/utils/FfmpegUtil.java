package io.github.franzli347.foss.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
    }

    /**
     * 打印日志，调试阶段可解开注释，观察执行情况
     *
     * @param line
     */
    private static void doNothing(String line) {
//    log.info(line);
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
    @SneakyThrows()
    public static MultimediaInfo getVideoInfo(URL url) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(url);
            return multimediaObject.getInfo();
        } catch (EncoderException e) {
            log.error("获取视频信息报错={}", e.getMessage());
            throw new RuntimeException("获取视频信息报错", e);
        }
    }
}

