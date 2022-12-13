package io.github.franzli347.toss.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * controller 日志切面
 * @author FranzLi
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * io.github.franzli347.toss.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求信息
        log.info("REQUEST URL : {} {} ", request.getMethod(), request.getRequestURL().toString());
        log.info("IP : {}", request.getRemoteAddr());
        log.debug("CLASS_METHOD : {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
     }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容和响应时间
        String respContent = Optional.ofNullable(ret).map(Object::toString).orElse("");
        log.info("RESPONSE : {}", respContent);
        log.debug("SPEND TIME : {} ms", (System.currentTimeMillis() - startTime.get()));
        startTime.remove();
    }


}
