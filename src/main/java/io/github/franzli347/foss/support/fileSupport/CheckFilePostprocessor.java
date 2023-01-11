package io.github.franzli347.foss.support.fileSupport;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.github.franzli347.foss.dto.FileUploadParam;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
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
