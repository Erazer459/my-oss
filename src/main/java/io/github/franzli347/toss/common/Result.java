package io.github.franzli347.toss.common;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    int code;
    T data;
    String msg;
}
