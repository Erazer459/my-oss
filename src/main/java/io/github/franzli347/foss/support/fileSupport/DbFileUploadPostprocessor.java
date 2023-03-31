package io.github.franzli347.foss.support.fileSupport;

import cn.hutool.core.util.IdUtil;
import io.github.franzli347.foss.model.dto.FileUploadParam;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.web.service.FilesService;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Path;

/**
 *
 *  数据库存储
 * @author FranzLi
 */

public class DbFileUploadPostprocessor implements FileUploadPostProcessor{

    private final FilesService filesService;

    private final BucketService bucketService;


    public DbFileUploadPostprocessor(FilesService filesService,BucketService bucketService) {
        this.filesService = filesService;
        this.bucketService = bucketService;
    }

    @SneakyThrows
    @Override
    public boolean process(String filePath, FileUploadParam param) {
        double fileSize = java.nio.file.Files.size(Path.of(filePath));
        String path = param.getBid() + File.separator + param.getName();
        boolean save = filesService.save(Files.builder()
                .id(IdUtil.getSnowflakeNextId())
                .bid(param.getBid())
                .fileName(param.getName())
                .path(path)
                .md5(param.getMd5())
                .fileSize(fileSize)
                .fileType(FileTypeHelper.getFileType(path))
                .build());
        boolean size = bucketService.updateBucketSizeWithFile(param.getBid(), fileSize);
        return save && size;
    }


}
