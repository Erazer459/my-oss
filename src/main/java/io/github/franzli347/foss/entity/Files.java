package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.franzli347.foss.common.ValidatedGroup;
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
    private Long id;

    @Size(max = 255, message = "编码长度不能超过255")
    private String fileName;

    private Double fileSize;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Size(max = 255, message = "编码长度不能超过255")
    private String path;

    private Integer bid;

    private String md5;

    private int fileType;

}
