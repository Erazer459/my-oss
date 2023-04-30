package io.github.franzli347.foss.protobuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.ByteString;
import io.github.franzli347.foss.grpc.file.DownloadResponse;
import io.github.franzli347.foss.grpc.file.FileTransferServiceGrpc;
import io.github.franzli347.foss.grpc.file.Response;
import io.github.franzli347.foss.grpc.file.Request;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FileTransferClient {

//    public static void main(String[] args) throws IOException, InterruptedException {
//        FileTransferClient client = new FileTransferClient(HOST, PORT);
//        client.upload("b.log", "D:/a.txt");
//        client.shutdown();
//    }
    private ManagedChannel managedChannel;

    private FileTransferServiceGrpc.FileTransferServiceBlockingStub blockingStub;

    public FileTransferClient(String HOST,int PORT) {
        this(ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext(true));
    }

    /**
     * 上传文件
     * @param name 保存到服务端的文件名
     * @param path 要上传的文件路径
     * @throws IOException
     */
    @SneakyThrows
    public void upload(String name, String path, String SnapshotPath) throws IOException {
        //name命名为/snapshotId/name
        Request request = Request.newBuilder()
                .setName(name)
                .setSnapshotPath(String.valueOf(SnapshotPath))
                // 文件 -> 字节码数据 -> ByteString
                .setFile(ByteString.copyFrom(getContent(path)))
                .build();
        Response response;
        try {
            response = blockingStub.upload(request);
            System.out.println(response.getMsg());
        } catch (StatusRuntimeException ex) {
        }
    }

    /**
     * 关闭客户端
     */
    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    FileTransferClient(ManagedChannelBuilder<?> channelBuilder) {
        managedChannel = channelBuilder.build();
        blockingStub = FileTransferServiceGrpc.newBlockingStub(managedChannel);
    }


    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        log.info("生成文件长度" + buffer.length);
        return buffer;
    }
    @SneakyThrows
    public void download(String serverPath, String clientPath){
        // 发送下载请求
        Request request = Request.newBuilder()
                .setName(serverPath)  // 文件名
                .build();
        DownloadResponse response = blockingStub.download(request);
        // 检查响应消息中是否成功接收文件
        if (response != null) {
            ByteString fileBytes = response.getFile();
            try {
                FileOutputStream outputStream = new FileOutputStream(clientPath);
                outputStream.write(fileBytes.toByteArray());  // 写入下载的文件
                outputStream.close();
            } catch (IOException e) {
                log.error("Failed to download file.");
                e.printStackTrace();
            }
        } else {
            log.error("Failed to download file.");
        }
    }
}
