package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.common.FileUploadParam;
import io.github.franzli347.foss.utils.FileZipUtil;
import org.apache.logging.log4j.util.Strings;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @ClassName FileCompressPostProcessor
 * @Author AlanC
 * @Date 2022/12/19 22:56
 **/
public class FileCompressPostProcessor implements FileUploadPostProcessor {
    @Override
    public boolean process(String filePath, FileUploadParam param) {
        return FileZipUtil.compress(filePath);
    }
}
