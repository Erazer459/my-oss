package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.model.dto.FileUploadParam;

public interface FileUploadPostProcessor {
     boolean process(String filePath, FileUploadParam param);
}
