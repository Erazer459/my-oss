package io.github.franzli347.foss.support;

import io.github.franzli347.foss.common.FileUploadParam;

public interface FileUploadPostProcessor {
     boolean process(String filePath, FileUploadParam param);
}
