package io.github.franzli347.foss.protobuf;
import com.google.protobuf.ByteString;
import io.github.franzli347.foss.grpc.file.DownloadResponse;
import io.github.franzli347.foss.grpc.file.FileTransferServiceGrpc;
import io.github.franzli347.foss.grpc.file.Request;
import io.github.franzli347.foss.grpc.file.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import io.grpc.Server;

import io.grpc.ServerBuilder;
import io.grpc.StatusRuntimeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileTransferServer {
    private Server server;


   @SneakyThrows
   public  FileTransferServer(@Value("${gRPC.Server.Port}")int port){
        start(port);
    }
    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new BasicCalImpl())
                .build()
                .start();
        // 添加钩子,在程序关闭时自动关闭服务端
        addHook();
        log.info("grpc server start success");
    }

    private void addHook() {
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    System.out.println("监听到JVM停止,正在关闭GRPC服务....");
                                    this.stop();
                                    System.out.println("服务已经停止...");
                                }));
    }

    /**
     * 关闭服务
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void await() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
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

    static class BasicCalImpl extends FileTransferServiceGrpc.FileTransferServiceImplBase {
        @SneakyThrows
        @Override
        public void upload(io.github.franzli347.foss.grpc.file.Request request,
                           io.grpc.stub.StreamObserver<Response> responseObserver) {
            byte[] bytes = request.getFile().toByteArray();
            log.info(String.format("收到文件%s长度%s", request.getName(), bytes.length));
            File f = new File(request.getName());
            Files.createDirectory(Path.of(request.getSnapshotPath()));
            Response response;
            if (f.exists()) {
                f.delete();
            }
            try (OutputStream os = new FileOutputStream(f)) {
                os.write(bytes);
                response = Response.newBuilder().setCode(1).setMsg("上传成功").build();
            } catch (IOException e) {
                response = Response.newBuilder().setCode(-1).setMsg(String.format("上传失败:%s", e.getMessage())).build();
                e.printStackTrace();
            }
            // 返回数据，完成此次请求
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void download(io.github.franzli347.foss.grpc.file.Request request,
              io.grpc.stub.StreamObserver<io.github.franzli347.foss.grpc.file.DownloadResponse>responseObserver){
            String filePath = request.getName(); // 拼接文件路径
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    byte[] fileBytes = Files.readAllBytes(file.toPath()); // 读取文件数据
                    ByteString byteString = ByteString.copyFrom(fileBytes);
                    DownloadResponse response = DownloadResponse.newBuilder()
                            .setFile(byteString)
                            .build();
                    responseObserver.onNext(response); // 发送响应消息
                    responseObserver.onCompleted();
                } catch (IOException e) {
                    responseObserver.onError(e);
                }
            } else {
                responseObserver.onError(new FileNotFoundException());
            }
        }
    }
}