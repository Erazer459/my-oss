package io.github.franzli347.foss.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @ClassName ProcessInfo
 * @Author AlanC
 * @Date 2022/12/29 15:39
 * @Description 压缩、上传、下载事件信息
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInfo {
    @Schema(description = "文件id")
    private String id;

    @Schema(description = "源")
    private long source;

    @Schema(description = "事件进度百分比")
    private double percentage;
    @Schema(description ="事件是否完成")
    @Builder.Default
    private boolean done=false;
}
