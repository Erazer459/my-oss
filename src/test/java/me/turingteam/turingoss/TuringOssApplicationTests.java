package me.turingteam.turingoss;

import org.csource.common.MyException;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@SpringBootTest
class TuringOssApplicationTests {

    @Test
    public void fileUpload() {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;

        try {
            // 加载配置文件，默认区classpath下加载
            ClientGlobal.init("fdfs_client.conf");
            // 创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            // 创建TrackerServer对象
            trackerServer = trackerClient.getTrackerServer();
            // 创建StorageServer对象
            storageServer = trackerClient.getStoreStorage(trackerServer);
            // 创建StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            // 上传文件，第一参数：本地路径，第二个参数：上传文件的后置，第三个参数：文件信息
            String[] uploadArray = storageClient.upload_file("E:\\ceodes\\turing-oss\\src\\test\\java\\me\\turingteam\\turingoss\\test.txt", "txt", null);
            Arrays.stream(uploadArray).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.getConnection().close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.getConnection().close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
