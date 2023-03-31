package io.github.franzli347.foss.support.fileSupport;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.web.service.FilesService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class DefaultFilePathResolver implements FilePathResolver {

    private final FilesService filesService;

    @Value("${downloadAddr}")
    private String downloadAddr;

    public DefaultFilePathResolver(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public String getFilePath(String fileMd5) {
        return downloadAddr + "/download/" + getFileId(fileMd5);
    }

    private Long getFileId(String fileMd5) {
        return Optional
                .ofNullable(filesService.getOne(new LambdaQueryWrapper<>(Files.class).eq(Files::getMd5, fileMd5)))
                .orElseThrow(() -> new RuntimeException("文件不存在"))
                .getId();
    }
}
