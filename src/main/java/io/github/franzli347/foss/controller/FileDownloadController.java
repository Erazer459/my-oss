package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.service.FileDownloadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @GetMapping("/download/{id}")
    public DeferredResult<ResponseEntity<StreamingResponseBody>> download(@PathVariable String id){
        return  fileDownloadService.download(id);
    }
}
