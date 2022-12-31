package io.github.franzli347.foss;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.entity.MyVideo;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.utils.FfmpegUtil;
import io.github.franzli347.foss.utils.FileZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ws.schild.jave.info.VideoInfo;
import ws.schild.jave.info.VideoSize;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class FileUploadServiceTest {
    @Autowired
    private FileUploadService fileUploadService;
    @Value("${pathMap.source}")
    private String path;
    private static String testId = "";
    public static Stream<FileUploadParam> fileUploadParamSource(){
        return Stream.of(FileUploadParam.builder()
                .id(testId)
                .uid("1")
                .name("tt.txt")
                .chunks(2)
                .md5(RandomUtils.nextLong(0, 1000000000) + "")
                .build());
    }

    public static final String displayName = "\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75\uD83E\uDD75";

    @ParameterizedTest
    @MethodSource("fileUploadParamSource")
    @Order(1)
    @DisplayName(displayName)
//    @Rollback(false)
    public void testInitMultipartUpload(FileUploadParam param) {
        Result result = fileUploadService.initMultipartUpload(param);
        Assertions.assertNotNull(result.getData());
        log.info("testInitMultipartUpload:{}",result);
        testId = result.getData().toString();
    }


    @ParameterizedTest
    @DisplayName(displayName)

    @MethodSource("fileUploadParamSource")
    @Order(2)
    public void testCheck(FileUploadParam param)  {
        Result result = fileUploadService.check(param.getId());
        log.info("testCheck:{}",result);
    }

//    @ParameterizedTest
//    @MethodSource("fileUploadParamSource")
//    @Order(3)
//    @DisplayName(displayName)
//    //@Rollback(false)
//    public void testUploadChunk(FileUploadParam param) throws IOException {
//        param.setFile(new MockMultipartFile("file", "test.txt", "text/plain", "turing".getBytes()));
//        param.setChunk(1);
//        log.info("param {}",param);
//        Result uploadChunk = fileUploadService.uploadChunk(param);
//        Assertions.assertEquals("upload " + "tt.txt" + " chunk " + 1 + " success",uploadChunk.getMsg());
//        log.info("testUploadChunk1:{}",uploadChunk);
//        //chunk2 upload
//        log.info("param {}",param);
//        param.setFile(new MockMultipartFile("file", "test.txt", "text/plain", " team".getBytes()));
//        param.setChunk(2);
//        Result uploadChunk2 = fileUploadService.uploadChunk(param);
//        Assertions.assertEquals("upload complete",uploadChunk2.getMsg());
//        log.info("testUploadChunk2:{}",uploadChunk2);
//        byte[] bytes = Files.readAllBytes(Path.of(path + "tt.txt"));
//        String s = new String(bytes);
//        Assertions.assertEquals("turing team",s);
//        Files.delete(Path.of(path + "tt.txt"));
//    }
//    @ParameterizedTest
//    @ValueSource(strings = "E:\\videoCompress\\God Knows[4K_FLAC_ASS].mkv")
//    @Order(3)
//    @DisplayName(displayName)
//    //@Rollback(value = true)
//    public void testCompress(String filePath){
//        File file = new File(filePath);
//        long old=file.length();
//        log.info("sourceObject:{}",old);
//        ProcessInfo info = ProcessInfo.builder().id(114514).build();
//        boolean compress=FileZipUtil.videoCompress(filePath, VideoCompressArgs.builder().frameRate(25).bitRate(500000).videoSize(new VideoSize(1920,1080)).build(), info, new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {//持续监听压缩进度
//                log.info("完成:{}", ((ProcessInfo) evt.getSource()).isDone());
//                log.info("百分比:{}",((ProcessInfo) evt.getSource()).getPercentage());
//            }
//        });
//        long newOne=file.length();
//        log.info("compress success:{}",compress);
//        log.info("compressed size:{}",newOne);
//    }
//    @ParameterizedTest
//    @ValueSource(strings = "E:\\imageCompress\\eso1242a.tif")
//    @Order(3)
//    @DisplayName(displayName)
//    public void imageCompress(String imagePath) throws IOException {
//        File file = new File(imagePath);
//        log.info("source:{}",file.length());
//        boolean flag=FileZipUtil.imageCompress(imagePath);
//        log.info("图片压缩成功?:{}",flag);
//        log.info("compressed:{}",file.length());
//    }
//@ParameterizedTest
//    @ValueSource(strings = "E:\\videoCompress\\God Knows[4K_FLAC_ASS].mkv")
//    @Order(4)
//    @DisplayName(displayName)
//    public void getInfoTest(String videoPath){
//        log.info("视频信息:{}",FfmpegUtil.getVideoInfo(videoPath).toString());
//}

}

