package io.github.franzli347.foss.support.fileSupport;

import org.springframework.web.multipart.MultipartFile;

public interface FileTransferResolver {
    public void transferFile(MultipartFile file, String target);
}
