package io.github.franzli347.foss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileUploadService {

    String initMultipartUpload(int uid,
                               int bid,
                               String name,
                               int chunks,
                               String md5,
                               long size);

    Set<String> check(String id);

    String uploadChunk(String id,
                       int uid,
                       int bid,
                       String name,
                       int chunks,
                       int chunk,
                       Long size,
                       String md5,
                       MultipartFile file);
}
