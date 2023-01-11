package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.service.FileDownloadService;
import io.swagger.v3.oas.annotations.Operation;
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

/*
    @GetMapping("/download/{id}")
    @Operation(summary = "小文件下载接口(不可断点续传不可分块")
    public DeferredResult<ResponseEntity<StreamingResponseBody>> download(@PathVariable String id) {
        return fileDownloadService.download(id);
    }
*/

    @GetMapping("/download/{id}")
    @Operation(summary = "文件下载接口(可断点续传可分块")
    @SneakyThrows
    public void getDownload(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        fileDownloadService.download(id, request, response);
    }


    @GetMapping(value = "/player/{id}")
    @SneakyThrows
    public void player(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        //TODO:RESOLVEPATH
        fileDownloadService.player(id, request, response);
    }

}
