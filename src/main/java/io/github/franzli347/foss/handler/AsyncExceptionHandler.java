package io.github.franzli347.foss.handler;

/**
 * @ClassName AsyncExceptionHandler
 * @Author AlanC
 * @Date 2022/12/31 18:36
 * @Description 由于timerTask的run没法抛出异常,所以就有了这个handler
 **/

import com.alibaba.fastjson2.JSON;
import io.github.franzli347.foss.exception.AsyncException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {//异步异常handler

    WebSocketHandler webSocketHandler;
    public AsyncExceptionHandler(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(params));
        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            log.info("asyncException:{}",asyncException.getResult().getMsg());
            webSocketHandler.sendResultMsg(asyncException.getUserId(),asyncException.getResult());
        }
        log.info("Exception :");
        ex.printStackTrace();
    }
}

