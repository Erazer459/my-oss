package io.github.franzli347.foss.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BucketWithPriviledge {

    private Integer id;

    private String name;

    private Double totalSize;

    private Integer uid;

    private Double usedSize;

    private LocalDateTime createTime;

    private String privilege;
}
