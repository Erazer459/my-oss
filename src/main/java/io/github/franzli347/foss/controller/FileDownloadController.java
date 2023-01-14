package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.service.FileDownloadService;
import io.github.franzli347.foss.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author FranzLi
 */
@Tag(name = "文件下载模块")
@RestController
@Slf4j
@Validated
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/download/{bid}/{id}")
    @Operation(summary = "文件下载/视频播放接口(可断点续传可分块,请求头里面带上inline:false/true来设置Content-disposition响应头的值)")
    @SneakyThrows
    public void getDownload(@PathVariable @Parameter(description = "bucket id") @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket不存在") String bid,
                            @PathVariable @Parameter(description = "文件id") @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id,
                            HttpServletRequest request,
                            HttpServletResponse response){
        boolean inline = Boolean.getBoolean(Optional.ofNullable(request.getHeader("inline")).orElse("true"));
        fileDownloadService.download(id,bid, inline,request, response);
    }

}
