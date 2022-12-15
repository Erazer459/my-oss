package io.github.franzli347.foss.support;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 *
 *  数据库存储
 * @author FranzLi
 */
@Component
public class DBFileUploadPostprocessor implements FileUploadPostProcessor{

    private final FilesService filesService;

    public DBFileUploadPostprocessor(FilesService filesService) {
        this.filesService = filesService;
    }

    @SneakyThrows
    @Override
    public boolean process(String filePath, FileUploadParam param) {
        Files build = Files
                .builder()
                .bid(param.getBid())
                .fileName(param.getName())
                .path(filePath)
                .fileSize((double) java.nio.file.Files.size(Path.of(filePath)))
                .build();
        return filesService.save(build);
    }
}
