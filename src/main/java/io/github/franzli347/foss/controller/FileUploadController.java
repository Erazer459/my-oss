package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.FileConstant;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.service.FileUploadService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * @author FranzLi
 */
@RestController
@RequestMapping("/upload")
@Tag(name = "文件上传模块")
@Validated
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    private final LoginUserProvider loginUserProvider;


    public FileUploadController(FileUploadService fileUploadService, LoginUserProvider loginUserProvider) {
        this.fileUploadService = fileUploadService;
        this.loginUserProvider = loginUserProvider;
    }

    /**
     * 文件上传初始化
     *
     * @return Result
     */
    @PostMapping("/initMultipartUpload")
    @Operation(summary = "文件上传初始化")
    public boolean initMultipartUpload(@Parameter(description = "目标桶id")
                                       @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在") Integer bid,
                                       @Parameter(description = "文件名")
                                            @Pattern(regexp = FileConstant.ILLEGAL_FILE_RE,message = FileConstant.FILE_NAME_ILLEGAL_MSG) String name,
                                       @Parameter(description = "分块数量")
                                           @Min(value = FileConstant.MIN_CHUNKS_NUM,message = FileConstant.MIN_CHUNKS_MSG) int chunks,
                                       @Parameter(description = "文件md5") String md5,
                                       @Parameter(description = "文件大小(单位Byte)")
                                           @Min(value = FileConstant.MIN_FILE_SIZE,message = FileConstant.MIN_FILE_SIZE_MSG) long size) {
        return fileUploadService.initMultipartUpload(loginUserProvider.getLoginUser().getId(), bid, name, chunks, md5, size);
    }


    @Operation(summary = "检查是否可以秒传(如果可以秒传会直接保存到目标桶)")
    @PostMapping("/secUpload/{md5}/{targetBid}")
    public boolean secUpload(@PathVariable @Parameter(description = "文件md5") String md5,
                             @PathVariable @Parameter(description = "目标桶id")
                             @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在") String targetBid) {
        return fileUploadService.secUpload(md5, targetBid);
    }


    /**
     * 分块上传
     *
     * @return Result
     */
    @Operation(summary = "上传分块")
    @PostMapping("/uploadChunk")
    public String uploadChunk(@Parameter(description = "目标桶id")
                              @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在") int bid,
                              @Parameter(description = "文件名")
                                    @Pattern(regexp = FileConstant.ILLEGAL_FILE_RE,message = FileConstant.FILE_NAME_ILLEGAL_MSG) String name,
                              @Parameter(description = "分块数量")
                                  @Min(value = FileConstant.MIN_CHUNKS_NUM,message = FileConstant.MIN_CHUNKS_MSG) int chunks,
                              @Parameter(description = "当前上传分块序号") int chunk,
                              @Parameter(description = "文件大小(总文件大小，不是分块之后的)")
                                  @Min(value = FileConstant.MIN_FILE_SIZE,message = FileConstant.MIN_FILE_SIZE_MSG) Long size,
                              @Parameter(description = "文件md5(不是分块之后的)") String md5,
                              @Parameter(description = "分块对象") MultipartFile file) {
        return fileUploadService.uploadChunk(loginUserProvider.getLoginUser().getId(), bid, name, chunks, chunk, size, md5, file);
    }

    /**
     * 检查上传任务已上传块数
     *
     * @return Result
     */
    @Operation(summary = "检查上传任务已上传块数")
    @PostMapping("/check/{md5}")
    public Set<String> check(@PathVariable @Parameter(description = "任务id") String md5) {
        return fileUploadService.check(md5);
    }


    @Operation(summary = "放弃上传任务")
    @PostMapping("/abort/{md5}")
    public boolean abort(@PathVariable @Parameter(description = "文件md5") final String md5) {
        return fileUploadService.abort(md5);
    }

    @Operation(summary = "小文件上传(小于10m),直接上传")
    @PostMapping("/smallFileUpload")
    public boolean smallFileUpload(@Parameter(description = "目标桶id")
                                   @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在") int bid,
                                   @Parameter(description = "文件名")
                                   @Pattern(regexp = FileConstant.ILLEGAL_FILE_RE,message = FileConstant.FILE_NAME_ILLEGAL_MSG) String name,
                                   @Parameter(description = "文件大小(总文件大小，不是分块之后的)") Long size,
                                   @Parameter(description = "文件md5(不是分块之后的)") String md5,
                                   @Parameter(description = "分块对象") MultipartFile file) {
        return fileUploadService.smallFileUpload(loginUserProvider.getLoginUser().getId(), bid, name, size, md5, file);
    }

}
