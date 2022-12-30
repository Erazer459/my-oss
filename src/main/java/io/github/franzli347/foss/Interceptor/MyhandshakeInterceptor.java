package io.github.franzli347.foss.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyhandshakeInterceptor
 * @Author AlanC
 * @Date 2022/12/29 17:58
 * @Description websocket握手拦截器
 **/
@Component
@Slf4j
public class MyhandshakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("握手开始");
        // 获得请求参数
        String token = request.getHeaders().get("token").toString();//TODO 完成鉴权后在redis中确认token是否存在
        if (StringUtils.isNotBlank(token)) {
            // 放入属性域
            attributes.put("token", token);
            log.info("用户 token:{} 握手成功！",token);
            return true;
        }
        log.info("用户验证失败");
        return false;
    }

    /**
     * 握手后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手完成");
    }

}
