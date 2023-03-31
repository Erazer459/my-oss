package io.github.franzli347.foss.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FranzLi
 */
@Tag(name = "文件下载模块")
@RestController
@Slf4j
@Validated
public class FileDownloadController {
 // 废弃 转到 nginx
//    private final FileDownloadService fileDownloadService;
//
//    public FileDownloadController(FileDownloadService fileDownloadService) {
//        this.fileDownloadService = fileDownloadService;
//    }
//
//    @GetMapping("/download/{bid}/{id}")
//    @Operation(summary = "文件下载/视频播放接口(可断点续传可分块,请求头里面带上inline:false/true来设置Content-disposition响应头的值)")
//    @SneakyThrows
//    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.READWRITE,AuthConstant.ONLYREAD,AuthConstant.OWNER})
//    public void getDownload(@PathVariable @Parameter(description = "bucket id") @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket不存在") String bid,
//                            @PathVariable @Parameter(description = "文件id") @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id,
//                            HttpServletRequest request,
//                            HttpServletResponse response){
//        boolean inline = Boolean.getBoolean(Optional.ofNullable(request.getHeader("inline")).orElse("true"));
//        fileDownloadService.download(id,bid, inline,request, response);
//    }



}
