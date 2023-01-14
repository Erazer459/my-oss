package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author FranzLi
 */
@RestController
@RequestMapping("/files")
@Tag(name = "文件操作模块")
@Validated
public class FilesController {

    FilesService filesService;

    public FilesController(FilesService filesService){
        this.filesService = filesService;
    }

    @DeleteMapping("/delete/{bid}/{id}")
    @Operation(summary = "删除文件")
    public boolean remove(@PathVariable @FiledExistInTable(colum = "id",serviceClz = FilesService.class,message = "文件不存在") String id,
                          @PathVariable @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket不存在")  String bid){
        return filesService.removeFilesById(id,bid);
    }

}
