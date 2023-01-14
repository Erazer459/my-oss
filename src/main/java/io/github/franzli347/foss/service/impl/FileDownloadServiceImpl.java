package io.github.franzli347.foss.service.impl;



import cn.hutool.core.util.StrUtil;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FileDownloadService;
import io.github.franzli347.foss.service.FilesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;


/**
 * @author FranzLi
 */
@Service
@Slf4j
public class FileDownloadServiceImpl implements FileDownloadService {

    @Value("${pathMap.source}")
    private String filePath;
    private final FilesService filesService;
    public FileDownloadServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }
    @Override
    @SneakyThrows
    public void download(String id, Boolean inline, HttpServletRequest request, HttpServletResponse response) {
        // Get your file stream from wherever.
        log.debug("id=" + id);
        Files files = Optional.ofNullable(filesService.getById(id))
                .orElseThrow(() -> new RuntimeException("文件不存在"));
        String fullPath = filePath + files.getPath();
        log.debug("下载路径:" + fullPath);
        File downloadFile = new File(fullPath);
        ServletContext context = request.getServletContext();
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        // set content attributes for the response
        response.setContentType(mimeType);
        // response.setContentLength((int) downloadFile.length()); set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("%s; filename=\"%s\"", downloadFile.getName(),Boolean.TRUE.equals(inline) ? "inline":"attachment");
        response.setHeader(headerKey, headerValue);
        // 解析断点续传相关信息
        response.setHeader("Accept-Ranges", "bytes");
        long downloadSize = downloadFile.length();
        long fromPos = 0;
        long toPos = 0;
        if (request.getHeader("Range") == null) {
            response.setHeader("Content-Length", downloadSize + "");
        } else {
            // 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String range = request.getHeader("Range");
            String bytes = range.replaceAll("bytes=", "");
            String[] ary = bytes.split("-");
            fromPos = Long.parseLong(ary[0]);
            if (ary.length == 2) {
                toPos = Long.parseLong(ary[1]);
            }
            int size;
            if (toPos > fromPos) {
                size = (int) (toPos - fromPos);
            } else {
                size = (int) (downloadSize - fromPos);
            }
            response.setHeader("Content-Length", size + "");
            downloadSize = size;
        }
        // Copy the stream to the response's output stream.
        try(RandomAccessFile in = new RandomAccessFile(downloadFile, "rw");
            OutputStream out = response.getOutputStream()){
            // 设置下载起始位置
            if (fromPos > 0) {
                in.seek(fromPos);
            }
            // 缓冲区大小
            int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
            byte[] buffer = new byte[bufLen];
            int num;
            int count = 0;
            // 当前写到客户端的大小
            while ((num = in.read(buffer)) != -1) {
                out.write(buffer, 0, num);
                count += num;
                //处理最后一段，计算不满缓冲区的大小
                if (downloadSize - count < bufLen) {
                    bufLen = (int) (downloadSize - count);
                    if (bufLen == 0) {
                        break;
                    }
                    buffer = new byte[bufLen];
                }
            }
            response.flushBuffer();
        }
    }

}
