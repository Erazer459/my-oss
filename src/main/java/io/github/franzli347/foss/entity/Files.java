package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName tb_files
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Files implements Serializable {

    @NotNull(message = "[]不能为空")
    @TableId
    private Integer id;

    @NotBlank(message = "[]不能为空")
    @Size(max = 255, message = "编码长度不能超过255")
    private String fileName;

    @NotNull(message = "[]不能为空")
    private Double fileSize;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Size(max = 255, message = "编码长度不能超过255")
    private String path;

    @NotNull(message = "[]不能为空")
    private Integer bid;

    private String md5;


}
