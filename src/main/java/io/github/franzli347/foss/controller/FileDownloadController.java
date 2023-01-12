package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.service.FileDownloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author FranzLi
 */
@Tag(name = "文件下载模块")
@RestController
@Slf4j
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }


    @GetMapping("/download/{id}/{inline}")
    @Operation(summary = "文件下载/视频播放接口(可断点续传可分块)")
    @SneakyThrows
    public void getDownload(@PathVariable @Parameter(description = "文件id") String id,
                            @PathVariable @Parameter(description = "Content-disposition响应头是inline还是attachment") Boolean inline,
                            HttpServletRequest request, HttpServletResponse response){
        fileDownloadService.download(id, inline,request, response);
    }



}
