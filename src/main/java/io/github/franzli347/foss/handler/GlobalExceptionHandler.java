package io.github.franzli347.foss.handler;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.exception.AsyncException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * @author FranzLi
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        log.error("exception occur reason {} on request method {}",e.getMessage(),req.getMethod());
        e.printStackTrace();
        return Result.builder().code(500).msg(e.getMessage()).build();
    }
    @ExceptionHandler(value = AsyncException.class)
    @ResponseBody
    public Result AsyncExceptionHandler(HttpServletRequest req,AsyncException asyncException){
        log.error("AsyncException occur reason{} on request method{}",asyncException.getErrorMessage(),req.getMethod());
        asyncException.printStackTrace();
        return Result.builder().code(500).msg(asyncException.getMessage()).build();
    }
}

