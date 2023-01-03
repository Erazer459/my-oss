package io.github.franzli347.foss.service.impl;

import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FileDownloadService;
import io.github.franzli347.foss.service.FilesService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author FranzLi
 */
@Service
public class FileDownloadServiceImpl implements FileDownloadService {


    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>());


    private final FilesService filesService;

    public FileDownloadServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }


    @SneakyThrows
    @Override
    public DeferredResult<ResponseEntity<StreamingResponseBody>> download(String id) {
        DeferredResult<ResponseEntity<StreamingResponseBody>> deferredResult = new DeferredResult<>();
        executor.submit(() -> {
            try {

                Files files = Optional.ofNullable(filesService.getById(id))
                        .orElseThrow(() -> new RuntimeException("文件不存在"));
                File file = new File(files.getPath());
                InputStream inputStream = new FileInputStream(file);
                StreamingResponseBody stream = outputStream -> {
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        outputStream.write(data, 0, nRead);
                    }
                };
                deferredResult.setResult(ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getFileName() + "\"")
                        .body(stream));
            } catch (IOException e) {
                deferredResult.setErrorResult(e);
            }

        });
        return deferredResult;
    }
}
