package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.common.FileUploadParam;
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
        Path p = Path.of(filePath);
        if (Files.isDirectory(p) || Files.exists(p) || p == null || Strings.isEmpty(filePath)) {
            return false;
        }
        File blkFile = new File(filePath);
        try {
            byte[] bFile = Files.readAllBytes(blkFile.toPath());
            ByteArrayOutputStream xzOutput = new ByteArrayOutputStream();
            XZOutputStream xzStream = new XZOutputStream(xzOutput, new LZMA2Options(LZMA2Options.PRESET_MAX));
            xzStream.write(bFile);
            xzStream.close();
            FileChannel result = new FileOutputStream(blkFile, false).getChannel();
            result.transferFrom((ReadableByteChannel) xzOutput, 0, xzOutput.size());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
