package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * @author FranzLi
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
/**
 * @author FranzLi
 * @description 用户基类
 */
public class UserBase {
        @TableId(type = IdType.INPUT)
        @Hidden
        private Integer id;

        private String username;

}
