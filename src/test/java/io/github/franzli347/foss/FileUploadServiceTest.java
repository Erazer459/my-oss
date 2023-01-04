package io.github.franzli347.foss;

import io.github.franzli347.foss.dto.FileUploadParam;
import io.github.franzli347.foss.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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
import java.util.Set;
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
                .bid(1)
                .uid(1)
                .name("tt.txt")
                .chunks(2)
                .md5(RandomUtils.nextLong(0, 1000000000) + "")
                .build());
    }

    @ParameterizedTest
    @MethodSource("fileUploadParamSource")
    @Order(1)
    void testInitMultipartUpload(FileUploadParam param) {
        String taskId = fileUploadService.initMultipartUpload(param.getUid(), param.getBid(), param.getName(), param.getChunks(), param.getMd5(), param.getSize());
        Assertions.assertNotNull(taskId);
        log.info("taskId:{}",taskId);
        testId = taskId;
    }


    @ParameterizedTest
    @MethodSource("fileUploadParamSource")
    @Order(2)
    void testCheck(FileUploadParam param)  {
        Set<String> set= fileUploadService.check(param.getId());
        log.info("testCheck:{}",set);
    }

    @ParameterizedTest
    @MethodSource("fileUploadParamSource")
    @Order(3)
    void testUploadChunk(FileUploadParam param) throws IOException {
        String result = fileUploadService.uploadChunk(param.getId(),
                param.getUid(),
                param.getBid(),
                param.getName(),
                param.getChunks(),
                1,
                param.getSize(),
                param.getMd5(),
                new MockMultipartFile("file", "test.txt", "text/plain", "turing".getBytes()));
        Assertions.assertEquals("upload[tt.txt]chunk[1]success",result);

        log.info("testUploadChunk1:{}",result);
        //chunk2 upload
        result = fileUploadService.uploadChunk(param.getId(),
                param.getUid(),
                param.getBid(),
                param.getName(),
                param.getChunks(),
                2,
                param.getSize(),
                param.getMd5(),
                new MockMultipartFile("file", "test.txt", "text/plain", " team".getBytes())
        );
        Assertions.assertEquals("http://localhost/1/tt.txt",result);
        log.info("testUploadChunk2:{}",result);
        byte[] bytes = Files.readAllBytes(Path.of(path + "\\" + param.getBid() + "\\" + "tt.txt"));
        String s = new String(bytes);
        Assertions.assertEquals("turing team",s);
        Files.delete(Path.of(path + "\\" + param.getBid() + "\\" + "tt.txt"));
    }

}

