package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.service.FileZipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    public FileZipController(FileZipService fileZipService) {
        this.fileZipService = fileZipService;
    }

    /**
     * @Author AlanC
     * @Description 视频压缩
     * @Date 15:28 2022/12/28
     * @Param [vid, compressArgs]
     * @return
     **/
    @Operation(summary = "视频压缩")
    @PostMapping("/compress/video/{vid}")
    @Parameter(name = "vid",description = "视频文件id",required = true)
    @Parameter(name = "compressArgs",description = "视频压缩参数",required = false)
    public Result videoCompress(@PathVariable int vid, @RequestBody VideoCompressArgs compressArgs){
        return fileZipService.videoCompress(vid,compressArgs);
    }
    /**
     * @Author AlanC
     * @Description  文件批量压缩
     * @Date 15:54 2022/12/28
     * @Param [iList]
     * @return
     **/
    @Operation(summary = "图片批量压缩")
    @PostMapping("/compress/image")
    @Parameter(name = "iList",description = "图片id集合",required = true)
    public Result imageCompress(@RequestBody List<Integer> iList){
        return fileZipService.imageCompress(iList);
    }

}
