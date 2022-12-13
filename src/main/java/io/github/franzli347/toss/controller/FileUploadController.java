package io.github.franzli347.toss.controller;

import io.github.franzli347.toss.common.FileUploadParam;
import io.github.franzli347.toss.common.Result;
import io.github.franzli347.toss.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// check -> initMultipartUpload
@RestController
@RequestMapping("/fileUpload")
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    /**
     * 文件上传初始化，返回上传任务id
     *
     * @param param
     * @return Result
     */
    @PostMapping("/initMultipartUpload")
    public Result initMultipartUpload(@RequestBody FileUploadParam param) {
        return fileUploadService.initMultipartUpload(param);
    }

    /**
     * 分块上传
     * @param param
     * @return
     */
    @PostMapping("/uploadChunk")
    public Result uploadChunk(FileUploadParam param) {
        return fileUploadService.uploadChunk(param);
    }

    /**
     * 检查上传任务当前块数
     * @param param
     * @return Result
     */
    @PostMapping("/check")
    public Result check(@RequestBody FileUploadParam param) {
        return fileUploadService.check(param);
    }


}
