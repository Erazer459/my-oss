package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.ValidatedGroup;
import io.github.franzli347.foss.service.BucketService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
    @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket id不存在",groups = {ValidatedGroup.Update.class,ValidatedGroup.Remove.class})
    @TableId()
    @Schema(description = "id(除了修改都不用传)")
    @NotNull(message = "id不能为空",groups = {ValidatedGroup.Update.class})
    private Integer id;

    @Schema(description = "bucket名")
    private String name;

    @Schema(description = "bucket容量")
    private Double totalSize;
    @Schema(description = "bucket拥有者id")
    @Null(message = "ownerId禁止修改",groups = {ValidatedGroup.Update.class})
    private Integer uid;

    @Schema(description = "bucket已用容量",hidden = true)
    private Double usedSize;

    @Schema(description = "bucket创建时间",hidden = true)
    @Null(message = "createTime禁止修改",groups = {ValidatedGroup.Update.class})
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
