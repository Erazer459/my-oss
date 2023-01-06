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
     * 文件上传初始化
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

        boolean initStatus = fileUploadService.initMultipartUpload(
                uid,
                bid,
                name,
                chunks,
                md5,
                size);
        return Result.builder().code(ResultCode.CODE_SUCCESS)
                .data(initStatus)
                .build();
    }


    @Operation(summary = "检查是否可以秒传")
    @PostMapping("/secUpload/{md5}/{targetBid}")
    public Result secUpload(@PathVariable String md5,@PathVariable String targetBid){
        boolean secUploadStatus = fileUploadService.secUpload(md5,targetBid);
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(secUploadStatus)
                .build();
    }



    /**
     * 分块上传
     * @return Result
     */
    @Operation(summary = "上传分块")
    @PostMapping("/uploadChunk")
    public Result uploadChunk(int uid,
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
                .data(fileUploadService.uploadChunk(uid,bid,name,chunks,chunk,size,md5,file))
                .build();
    }

    /**
     * 检查上传任务已上传块数
     * @return Result
     */
    @Operation(summary = "检查上传任务已上传块数")
    @PostMapping("/check/{md5}")
    public Result check(@PathVariable @Parameter(description = "任务id") String md5) {
        return Result.builder()
                .data(fileUploadService.check(md5))
                .code(ResultCode.CODE_SUCCESS).build();
    }


    @Operation(summary = "放弃上传任务")
    @PostMapping("/abort/{md5}")
    public Result abort(@PathVariable final String md5){
        boolean abort = fileUploadService.abort(md5);
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(abort)
                .build();
    }


}
