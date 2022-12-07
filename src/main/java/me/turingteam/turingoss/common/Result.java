package me.turingteam.turingoss.common;

import  me.turingteam.turingoss.common.ResultCode;
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
