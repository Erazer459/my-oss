package io.github.franzli347.foss.service;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.VideoCompressArgs;
import io.github.franzli347.foss.exception.AsyncException;

import java.io.IOException;
import java.util.List;

public interface FileZipService {
    Result videoCompress(int vid, VideoCompressArgs args, String id) throws AsyncException, IOException;

    Result imageCompress(List<Integer> iList,String id);
}
