package io.github.franzli347.foss.service;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;

import java.util.List;

public interface FileZipService {
    public Result videoCompress(int vid, VideoCompressArgs args);

    Result imageCompress(List<Integer> iList);
}
