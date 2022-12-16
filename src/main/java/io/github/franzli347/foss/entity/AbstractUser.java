package io.github.franzli347.foss.entity;

import lombok.*;

/**
 * @author FranzLi
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractUser {
        private int id;

        private String username;

        private String password;

}
