package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.dto.FileUploadParam;
import io.github.franzli347.foss.utils.FileZipUtil;

/**
 * @ClassName FileCompressPostProcessor
 * @Author AlanC
 * @Date 2022/12/19 22:56
 **/
public class FileCompressPostProcessor implements FileUploadPostProcessor {
    @Override
    public boolean process(String filePath, FileUploadParam param) {
        return FileZipUtil.compress(filePath);
    }
}
