package io.github.franzli347.foss.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName tb_bucket
*/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Bucket implements Serializable {

    @NotNull(message="id不能为空")
    @TableId()
    @Schema(description = "id(除了修改都不用传)")
    private Integer id;

    @Schema(description = "bucket名")
    private String name;

    @Schema(description = "bucket容量")
    private Double totalSize;

    @Schema(description = "bucket拥有者id")
    private Integer uid;

    @Schema(description = "bucket已用容量",hidden = true)
    private Double usedSize;

    @Schema(description = "bucket创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
