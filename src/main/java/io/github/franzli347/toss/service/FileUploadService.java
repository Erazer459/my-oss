package io.github.franzli347.toss.service;

import io.github.franzli347.toss.common.FileUploadParam;
import io.github.franzli347.toss.common.Result;

public interface FileUploadService {

    Result initMultipartUpload(FileUploadParam param);

    Result check(FileUploadParam param);

    Result uploadChunk(FileUploadParam param);
}
