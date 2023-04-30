package io.github.franzli347.foss.web.service;

import io.github.franzli347.foss.model.dto.VideoCompressArgs;
import io.github.franzli347.foss.exception.AsyncException;

import java.io.IOException;

public interface FileZipService {
    void videoCompress(String vid, VideoCompressArgs args, String uid) throws AsyncException, IOException;

    void imageCompress(String imageId,int quality,String uid);


}
