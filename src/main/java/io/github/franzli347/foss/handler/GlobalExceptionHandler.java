package io.github.franzli347.foss.handler;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
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
        log.error("exception occur reason {} on request url {}",e.getMessage(),req.getRequestURI());
        e.printStackTrace();
        return Result.builder().code(ResultCode.CODE_ERROR).msg(e.getMessage()).build();
    }

}

