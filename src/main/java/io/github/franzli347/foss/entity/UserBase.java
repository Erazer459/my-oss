package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.Hidden;
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
        @TableId(type = IdType.INPUT)
        @Hidden
        @TableField("id")
        private int id;
        @TableField("username")
        private String username;

}
