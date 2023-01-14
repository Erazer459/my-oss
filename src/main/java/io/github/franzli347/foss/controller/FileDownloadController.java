package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.AuthConstant;
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


    @GetMapping("/download/{id}/{inline}")
    @Operation(summary = "文件下载/视频播放接口(可断点续传可分块)")
    @SneakyThrows
    public void getDownload(@PathVariable
                                @Parameter(description = "文件id")
                                @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id,
                            @PathVariable
                                @Parameter(description = "Content-disposition响应头是inline还是attachment") Boolean inline,
                            HttpServletRequest request, HttpServletResponse response){
        fileDownloadService.download(id, inline,request, response);
    }


    @GetMapping(value = "/player/{id}")
    @SneakyThrows
    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.OWNER,AuthConstant.ONLYREAD,AuthConstant.READWRITE})
    public void player(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        //TODO:RESOLVEPATH
        fileDownloadService.player(id, request, response);
    }

}
