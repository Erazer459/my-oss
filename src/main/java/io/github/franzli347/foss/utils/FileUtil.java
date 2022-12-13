package io.github.franzli347.foss.utils;

import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 文件工具类
 * @author FranzLi
 */
public class FileUtil {
    public static String getExtraName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getNameWithoutExtra(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static boolean mergeFiles(String[] filePath, String resultPath) {
        Path p = Path.of(resultPath);
        if (Files.isDirectory(p) || Files.exists(p) || filePath == null || filePath.length < 1 || Strings.isEmpty(resultPath)) {
            return false;
        }

        if (filePath.length == 1) {
            return new File(filePath[0]).renameTo(new File(resultPath));
        }
        File[] files = new File[filePath.length];

        for (int i = 0; i < filePath.length; i ++) {
            files[i] = new File(filePath[i]);
            if (Strings.isEmpty(filePath[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try(FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();) {
            for (int i = 0; i < filePath.length; i ++) {
                try(FileChannel blk = new FileInputStream(files[i]).getChannel()){
                    resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < filePath.length; i ++) {
            files[i].delete();
        }

        return true;
    }

}
