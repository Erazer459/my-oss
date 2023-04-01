package io.github.franzli347.foss.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.hibernate.validator.constraints.ScriptAssert;

/**
 * @ClassName RespHeaderCtrl
 * @Author AlanC
 * @Date 2023/3/30 22:10
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@TableName("tb_respheader_ctrl")
public class RespHeaderCtrl {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private int id;
    @Schema(description = "用户id")
    private  int uid;
    @Schema(description = "响应头名称")
    private String respheader;
    @Schema(description = "响应头值")
    private String value;

    public RespHeaderCtrl(int loginIdAsInt, String respheader, String value) {
        this.uid=loginIdAsInt;
        this.respheader=respheader;
        this.value=value;
    }
}
