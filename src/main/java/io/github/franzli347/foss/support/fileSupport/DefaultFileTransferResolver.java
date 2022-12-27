package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.utils.FileUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author FranzLi
 */
@Component
public class DefaultFileTransferResolver implements FileTransferResolver{
    @Override
    @SneakyThrows
    public void transferFile(MultipartFile file, String target) {
        // 如果暂存目录不存在创建暂存目录
        Path tmpPath = Path.of(FileUtil.getFilePathWithoutName(target));
        if(!Files.exists(tmpPath)){
            Files.createDirectories(tmpPath);
        }
        file.transferTo(Path.of(target));
    }
}
