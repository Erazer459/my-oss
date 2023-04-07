package io.github.franzli347.foss.model.vo;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilesVo {

    private String id;

    private String fileName;

    private Double fileSize;

    private LocalDateTime createTime;

    private Integer bid;

    private String md5;

    private int fileType;
}
