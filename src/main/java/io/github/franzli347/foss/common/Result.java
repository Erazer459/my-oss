package io.github.franzli347.foss.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author FranzLi
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    int code;
    Object data;
    String msg;
}
