package io.github.franzli347.foss.entity;

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
        private int id;

        private String username;

        private String password;

}
