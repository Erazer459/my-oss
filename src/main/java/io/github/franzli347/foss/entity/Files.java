package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.franzli347.foss.common.ValidatedGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author FranzLi
 * @TableName tb_files
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Files implements Serializable {

    @NotNull(message = "文件id不能为空",groups = {ValidatedGroup.Update.class})
    @Schema(description = "文件id")
    private Long id;

    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小")
    private Double fileSize;
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间(不用传)")
    private LocalDateTime createTime;
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "文件路径(物理磁盘中的相对路劲)")
    private String path;

    @Schema(description = "所属bucket的id")
    private Integer bid;

    @Schema(description = "文件md5")
    private String md5;

    @Schema(description = "文件类型 0:视频 1:图片 2:其他")
    private int fileType;

}
