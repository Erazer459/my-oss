package io.github.franzli347.foss.utils;

import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author AlanC
 * @Description 文件压缩工具类, LZMA2算法
 * @Date 17:28 2022/12/19
 * @return
 **/
public class FileZipUtil {

    private FileZipUtil(){}

    /**
     * @return
     * @Author AlanC
     * @Description 文件压缩
     * @Date 18:09 2022/12/19
     * @Param [filepath]
     **/
    @SneakyThrows
    public static boolean compress(String filepath) {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || Files.exists(p) || p == null || Strings.isEmpty(filepath)) {
            return false;
        }
        File blkFile = new File(filepath);
        byte[] bFile = Files.readAllBytes(blkFile.toPath());
        ByteArrayOutputStream xzOutput = new ByteArrayOutputStream();
        XZOutputStream xzStream = new XZOutputStream(xzOutput, new LZMA2Options(LZMA2Options.PRESET_MAX));
        xzStream.write(bFile);
        xzStream.close();
        FileChannel result = new FileOutputStream(blkFile, false).getChannel();
        result.transferFrom((ReadableByteChannel) xzOutput, 0, xzOutput.size());
        return true;
    }

    /**
     * @return boolean
     * @Author AlanC
     * @Description 文件解压
     * @Date 18:08 2022/12/19
     * @Param [filepath]
     **/
    @SneakyThrows
    public static boolean decompress(String filepath) {
        Path p = Path.of(filepath);
        if (Files.isDirectory(p) || Files.exists(p) || p == null || Strings.isEmpty(filepath)) {
            return false;
        }
            byte[] bFile = Files.readAllBytes(p);
            XZInputStream xzInputStream = new XZInputStream(new ByteArrayInputStream(bFile));
            byte firstByte = (byte) xzInputStream.read();
            byte[] buffer = new byte[xzInputStream.available()];
            buffer[0] = firstByte;
            xzInputStream.read(buffer, 1, buffer.length - 2);
            xzInputStream.close();
            Files.write(p, buffer);
        return true;
    }
}