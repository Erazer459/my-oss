package io.github.franzli347.foss;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

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

    @ParameterizedTest
    @MethodSource("fileUploadParamSource")
    @Order(3)
    @DisplayName(displayName)
//    @Rollback(false)
    public void testUploadChunk(FileUploadParam param) throws IOException {
        param.setFile(new MockMultipartFile("file", "test.txt", "text/plain", "turing".getBytes()));
        param.setChunk(1);
        log.info("param {}",param);
        Result uploadChunk = fileUploadService.uploadChunk(param);
        Assertions.assertEquals("upload " + "tt.txt" + " chunk " + 1 + " success",uploadChunk.getMsg());
        log.info("testUploadChunk1:{}",uploadChunk);
        //chunk2 upload
        log.info("param {}",param);
        param.setFile(new MockMultipartFile("file", "test.txt", "text/plain", " team".getBytes()));
        param.setChunk(2);
        Result uploadChunk2 = fileUploadService.uploadChunk(param);
        Assertions.assertEquals("upload complete",uploadChunk2.getMsg());
        log.info("testUploadChunk2:{}",uploadChunk2);

        byte[] bytes = Files.readAllBytes(Path.of(path + "tt.txt"));
        String s = new String(bytes);
        Assertions.assertEquals("turing team",s);
        Files.delete(Path.of(path + "tt.txt"));
    }

}

