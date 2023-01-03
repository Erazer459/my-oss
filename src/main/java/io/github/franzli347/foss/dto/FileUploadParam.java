package io.github.franzli347.foss.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FileUploadParam {
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 任务ID
     */
    private String id;

    /**
     * 总分片数量
     */
    private int chunks;

    /**
     * 当前分片大小
     */
    private long size;

    /**
     * bucket id
     */
    private Integer bid;
    /**
     * 文件名
     */
    private String name;
    /**
     * MD5
     */
    private String md5;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
