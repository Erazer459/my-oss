package io.github.franzli347.foss.web.service;

import io.github.franzli347.foss.model.dto.VideoCompressArgs;
import io.github.franzli347.foss.exception.AsyncException;

import java.io.IOException;

public interface FileZipService {
    void videoCompress(int vid, VideoCompressArgs args, String id) throws AsyncException, IOException;

    void imageCompress(int imageId,int quality,String id);


}
