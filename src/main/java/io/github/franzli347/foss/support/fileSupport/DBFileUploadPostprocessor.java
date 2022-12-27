package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.dto.FileUploadParam;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;
import lombok.SneakyThrows;

import java.nio.file.Path;

/**
 *
 *  数据库存储
 * @author FranzLi
 */

public class DBFileUploadPostprocessor implements FileUploadPostProcessor{

    private final FilesService filesService;

    public DBFileUploadPostprocessor(FilesService filesService) {
        this.filesService = filesService;
    }

    @SneakyThrows
    @Override
    public boolean process(String filePath, FileUploadParam param) {

        return filesService.save(
                Files
                .builder()
                .bid(param.getBid())
                .fileName(param.getName())
                .path(filePath)
                .md5(param.getMd5())
                .fileSize((double) java.nio.file.Files.size(Path.of(filePath)))
                .build()
        );
    }
}
