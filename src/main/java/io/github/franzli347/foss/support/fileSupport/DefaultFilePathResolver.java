package io.github.franzli347.foss.support.fileSupport;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;

import java.util.Optional;

public class DefaultFilePathResolver implements FilePathResolver {

    private final FilesService filesService;

    public DefaultFilePathResolver(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public String getFilePath(String fileMd5) {
        return  Optional
                .ofNullable(filesService.getOne(new LambdaQueryWrapper<>(Files.class).eq(Files::getMd5, fileMd5)))
                .orElseThrow(() -> new RuntimeException("文件不存在"))
                .getPath();
    }
}
