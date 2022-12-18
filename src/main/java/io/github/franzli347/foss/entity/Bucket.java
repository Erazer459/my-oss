package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    private Integer id;

    private String name;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Integer uid;


}
