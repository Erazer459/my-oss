package io.github.franzli347.foss.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileUploadParam {
    // 用户id
    private String uid;
    //任务ID
    private String id;
    //总分片数量
    private int chunks;
    //当前为第几块分片
    private int chunk;
    //当前分片大小
    private long size = 0L;
    //文件名
    private String name;
    //分片对象
    private MultipartFile file;
    // MD5
    private String md5;

}
