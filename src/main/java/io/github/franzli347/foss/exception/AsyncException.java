package io.github.franzli347.foss.exception;

import io.github.franzli347.foss.common.Result;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName AsyncException
 * @Author AlanC
 * @Date 2022/12/28 20:56
 **/
@Data
@AllArgsConstructor
public class AsyncException extends RuntimeException {//异步异常由AsyncExceptionHandler处理
    private Result result;
    private String userId;//获取websocketSession所需要的id
}
