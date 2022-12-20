package io.github.franzli347.foss.service;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.common.Result;

public interface FileUploadService {

    Result initMultipartUpload(FileUploadParam param);

    Result check(String id);

    Result uploadChunk(FileUploadParam param);
}
