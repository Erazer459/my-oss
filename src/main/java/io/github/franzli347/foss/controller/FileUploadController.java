package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fileUpload")
@Slf4j
@Tag(name = "文件上传模块")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    /**
     * 文件上传初始化，返回上传任务id
     *
     * @return Result
     */
    @PostMapping("/initMultipartUpload")
    @Operation(summary = "文件上传初始化")
/*    @Parameter(name = "uid", description = "用户id",required = true)
    @Parameter(name = "name", description = "文件名",required = true)
    @Parameter(name = "chunks", description = "分片数量",required = true)
    @Parameter(name = "md5", description = "整个文件md5",required = true)
    @Parameter(name = "size", description = "文件大小")*/
/*
            @Parameter(name = "chunk", description = "当前分片"),
            @Parameter(name = "file", description = "分片对象"),
            @Parameter(name = "id", description = "任务id"),
*/
    public Result initMultipartUpload(@RequestBody FileUploadParam param) {
        return fileUploadService.initMultipartUpload(param);
    }

    /**
     * 分块上传
     * @return
     */
    @Operation(summary = "上传分块(用form-data)")
/*    @Parameter(name = "id", description = "任务id",required = true)
    @Parameter(name = "uid", description = "用户id",required = true)
    @Parameter(name = "name", description = "文件名",required = true)
    @Parameter(name = "chunks", description = "分片数量",required = true)
    @Parameter(name = "chunk", description = "当前分片",required = true)
    @Parameter(name = "size",description = "当前分块大小",required = true)
    @Parameter(name = "md5", description = "整个文件md5",required = true)
    @Parameter(name = "file", description = "分片对象",required = true, schema = @Schema(type = "file"))*/
    @PostMapping("/uploadChunk")
    public Result uploadChunk(FileUploadParam param) {
        return fileUploadService.uploadChunk(param);
    }

    /**
     * 检查上传任务当前块数
     * @param id
     * @return Result
     */
    @Operation(summary = "检查上传任务当前块数")
    @Parameter(name = "id", description = "任务id",required = true)
    @PostMapping("/check/{id}")
    public Result check(@PathVariable String id) {
        return fileUploadService.check(id);
    }


}
