package io.github.franzli347.foss.common;

import lombok.*;

/**
 * @author FranzLi
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    int code;
    Object data;
    String msg;
}
