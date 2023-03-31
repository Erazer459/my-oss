package io.github.franzli347.foss.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author FranzLi
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
/**
 * @author FranzLi
 * @description 用户基类
 */
public class UserBase {
        @TableId(type = IdType.AUTO)
        @Hidden
        @TableField("id")
        private int id;
        @TableField("username")
        @Schema(description = "用户名")
        private String username;

        @TableField("email")
        private String email;
}
