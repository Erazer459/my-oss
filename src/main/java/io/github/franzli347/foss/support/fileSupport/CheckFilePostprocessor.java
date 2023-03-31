package io.github.franzli347.foss.support.fileSupport;

import cn.hutool.core.io.FileUtil;
import io.github.franzli347.foss.model.dto.FileUploadParam;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;
import java.nio.file.Files;

/**
 * md5校验文件
 * @author FranzLi
 */
public class CheckFilePostprocessor implements FileUploadPostProcessor{
    @Override
    @SneakyThrows
    public boolean process(String filePath, FileUploadParam param) {
        return param.getMd5().equals(DigestUtils.md5DigestAsHex(Files.newInputStream(FileUtil.file(filePath).toPath())));
    }
}
