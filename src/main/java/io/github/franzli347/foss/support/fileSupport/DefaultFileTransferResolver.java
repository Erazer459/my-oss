package io.github.franzli347.foss.support.fileSupport;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * @author FranzLi
 */
@Component
public class DefaultFileTransferResolver implements FileTransferResolver{
    @Override
    @SneakyThrows
    public void transferFile(MultipartFile file, String target) {
        file.transferTo(Path.of(target));
    }
}
