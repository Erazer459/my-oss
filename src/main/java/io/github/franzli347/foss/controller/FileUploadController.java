package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author FranzLi
 */
@RestController
@RequestMapping("/upload")
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
    public Result initMultipartUpload(int uid,
                                      int bid,
                                      String name,
                                      int chunks,
                                      String md5,
                                      long size) {
        return Result.builder().code(ResultCode.CODE_SUCCESS)
                .data(fileUploadService.initMultipartUpload(
                        uid,
                        bid,
                        name,
                        chunks,
                        md5,
                        size)
                )
                .build();
    }

    /**
     * 分块上传
     * @return Result
     */
    @Operation(summary = "上传分块")
    @PostMapping("/uploadChunk")
    public Result uploadChunk(String id,
                              int uid,
                              int bid,
                              String name,
                              int chunks,
                              int chunk,
                              Long size,
                              String md5,
                              MultipartFile file
                              ) {
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(fileUploadService.uploadChunk(id,uid,bid,name,chunks,chunk,size,md5,file))
                .build();
    }

    /**
     * 检查上传任务当前块数
     * @return Result
     */
    @Operation(summary = "检查上传任务当前块数")
    @PostMapping("/check/{id}")
    public Result check(@PathVariable @Parameter(description = "任务id") String id) {
        return Result.builder()
                .data(fileUploadService.check(id))
                .code(ResultCode.CODE_SUCCESS).build();
    }


}
