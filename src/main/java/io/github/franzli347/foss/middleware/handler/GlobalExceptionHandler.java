package io.github.franzli347.foss.middleware.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import io.github.franzli347.foss.model.vo.Result;
import io.github.franzli347.foss.common.constant.ResultCode;
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
    @ExceptionHandler(value = NotRoleException.class)
    @ResponseBody
    public Result AuthenticationExceptionHandler(NotRoleException e){
        return Result.builder().code(ResultCode.CODE_AUTH).msg("用户无权限").build();
    }
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public Result NotLoginExceptionHandler(NotLoginException e){
        return Result.builder().code(ResultCode.CODE_AUTH).msg("用户未登录").build();
    }

}

