package io.github.franzli347.foss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileUploadService {

    /**
     * 初始化上传任务
     * @param uid 用户id
     * @param bid bucket id
     * @param name 文件名
     * @param chunks 分块总数
     * @param md5 文件md5
     * @param size 文件大小
     * @return 任务id
     */
    String initMultipartUpload(final int uid,
                               final int bid,
                               final String name,
                               final int chunks,
                               final String md5,
                               final long size);

    /**
     * 检查任务状态
     * @param id 任务id
     * @return 已上传分块数
     */
    Set<String> check(String id);

    /**
     * 上传分块
     * @param id 任务id
     * @param uid  用户id
     * @param bid bucket id
     * @param name 文件名
     * @param chunks 分块总数
     * @param chunk 当前分块
     * @param size 文件大小
     * @param md5  文件md5
     * @param file 文件
     * @return 上传结果
     */
    String uploadChunk(final String id,
                       final int uid,
                       final int bid,
                       final String name,
                       final int chunks,
                       final int chunk,
                       final Long size,
                       final String md5,
                       final MultipartFile file);
}
