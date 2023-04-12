package io.github.franzli347.foss.web.service;

import io.github.franzli347.foss.model.vo.Result;
import org.springframework.scheduling.annotation.Async;
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
     * @return 初始化状态
     */
    boolean initMultipartUpload(final int uid,
                               final int bid,
                               final String name,
                               final int chunks,
                               final String md5,
                               final long size);

    /**
     * 检查任务状态
     * @param md5 文件md5
     * @return 已上传分块数
     */
    Set<String> check(String md5);

    /**
     * 上传分块
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
    Result uploadChunk(final int uid,
                       final int bid,
                       final String name,
                       final int chunks,
                       final int chunk,
                       final Long size,
                       final String md5,
                       final MultipartFile file);


    /**
     * 文件秒传检测
     *
     * @param md5       文件md5
     * @param targetBid
     * @return 文件地址
     */
    boolean secUpload(String md5, String targetBid);

    /**
     * @param md5 文件md5
     * @return 取消状态
     */
    boolean abort(String md5);

    boolean smallFileUpload(int uid, int bid, String name, long size, String md5, MultipartFile file);

    @Async("asyncExecutor")
    void copyFromOtherBucket(int sourceBucketId,int targetBucketId,String fileName);
}
