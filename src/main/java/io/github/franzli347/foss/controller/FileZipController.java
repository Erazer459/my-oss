package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.AuthConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.dto.VideoCompressArgs;
import io.github.franzli347.foss.service.FileZipService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName FileZipController
 * @Author AlanC
 * @Date 2022/12/26 0:02
 **/
@RestController
@RequestMapping("/compress")
@Slf4j
@EnableAsync
@Tag(name = "图片与视频压缩模块")
public class FileZipController {
    private final FileZipService fileZipService;
    private final LoginUserProvider loginUserProvider;

    public FileZipController(FileZipService fileZipService, LoginUserProvider loginUserProvider) {
        this.fileZipService = fileZipService;
        this.loginUserProvider = loginUserProvider;
    }

    /**
     * @Author AlanC
     * @Description 视频压缩
     * @Date 15:28 2022/12/28
     * @Param [vid, compressArgs]
     * @return
     **/
    @SneakyThrows
    @Operation(summary = "视频压缩")
    @PostMapping("/video/{vid}")
    @Parameter(name = "vid",description = "视频文件id",required = true)
    @Parameter(name = "compressArgs",description = "视频压缩参数",required = false)
    @CheckBucketPrivilege(spelString = "#vid",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.OWNER,AuthConstant.READWRITE})
    public Result videoCompress(@PathVariable int vid, @RequestBody VideoCompressArgs compressArgs){
        //TODO 完成鉴权后从拦截器获取principal的id
        fileZipService.videoCompress(vid,compressArgs, String.valueOf(loginUserProvider.getLoginUser().getId()));
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("视频压缩任务创建完毕").data(vid).data(compressArgs).build();
    }
    /**
     * @Author AlanC
     * @Description  图片压缩
     * @Date 15:54 2022/12/28
     * @Param imageId
     * @return
     **/
    @Operation(summary = "图片压缩")
    @PostMapping("/image/{imageId}/{quality}")
    @Parameter(name = "imageId",description = "图片id",required = true)
    @Parameter(name = "quality",description = "图片质量(无损仅限png为100,其余依次 75,45,10)")
    @CheckBucketPrivilege(spelString = "#imageId",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.OWNER,AuthConstant.READWRITE})
    public Result imageCompress(@PathVariable int imageId,@PathVariable int quality){
        fileZipService.imageCompress(imageId,quality,String.valueOf(loginUserProvider.getLoginUser().getId()));
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("图片压缩任务创建完毕").data(imageId).build();
    }

}
