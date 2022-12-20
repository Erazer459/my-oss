package io.github.franzli347.foss.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FileUploadParam {
    // 用户id
    @Schema(description = "用户id")
    private String uid;
    //任务ID
    @Schema(description = "任务ID")
    private String id;
    //总分片数量
    @Schema(description = "总分片数量")
    private int chunks;
    //当前为第几块分片
    @Schema(description = "当前为第几块分片")
    private int chunk;
    //当前分片大小
    @Schema(description = "当前分片大小")
    private long size = 0L;
    @Schema(description = "bucket")
    private Integer bid;

    //文件名
    @Schema(description = "文件名")
    private String name;
    //分片对象
    @Schema(description = "分片对象",type = "file")
    private MultipartFile file;
    // MD5
    @Schema(description = "文件MD5")
    private String md5;

    private LocalDateTime createTime;

}
