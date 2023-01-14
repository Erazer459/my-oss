package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName BucketPrivilege
 * @Author AlanC
 * @Date 2023/1/11 21:35
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_bucket_privilege")
public class BucketPrivilege {

    @TableId(type = IdType.INPUT)
    @Schema(description = "权限id(只在更新时使用)")
    private int id;

    @Schema(description = "权限创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Schema(description = "权限更新时间",hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "桶id")
    private int bid;

    @Schema(description = "权限拥有者id")
    private int uid;

    @Schema(description = "权限类型:只读或读写(r or rw)")
    private String privilege;


}
