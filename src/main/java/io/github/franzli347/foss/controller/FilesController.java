package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@Tag(name = "文件操作模块")
public class FilesController {

    FilesService filesService;

    public FilesController(FilesService filesService){
        this.filesService = filesService;
    }

    @DeleteMapping("/delete/{id}")
    public boolean remove(@PathVariable String id){
        return filesService.removeFilesById(id);
    }

    @PutMapping("/update")
    public boolean updateFilesName(@Parameter(description = "文件信息") String id,
                                  @Parameter(description = "文件名") String fileName){
        return filesService.updateFilesName(id,fileName);
    }

}
