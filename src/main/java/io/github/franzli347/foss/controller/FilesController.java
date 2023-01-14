package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.AuthConstant;
import io.github.franzli347.foss.service.FilesService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@Tag(name = "文件操作模块")
@Validated
public class FilesController {

    FilesService filesService;

    public FilesController(FilesService filesService){
        this.filesService = filesService;
    }

    @DeleteMapping("/delete/{id}")
    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.OWNER,AuthConstant.READWRITE})
    public boolean remove(@PathVariable
                              @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id){
        return filesService.removeFilesById(id);
    }

    @PutMapping("/update")
    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.FILE_ID,privilege = {AuthConstant.OWNER,AuthConstant.READWRITE})
    public boolean updateFilesName(@Parameter(description = "文件信息")
                                       @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id,
                                  @Parameter(description = "文件名") String fileName){
        return filesService.updateFilesName(id,fileName);
    }

}
