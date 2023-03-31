package io.github.franzli347.foss.config;

import io.github.franzli347.foss.middleware.Interceptor.MyhandshakeInterceptor;
import io.github.franzli347.foss.middleware.handler.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @ClassName WebSocketConfig
 * @Author AlanC
 * @Date 2022/12/29 15:09
 * @Description websocket配置类
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final MyhandshakeInterceptor myInterceptor;

    public WebSocketConfig(MyhandshakeInterceptor myInterceptor, WebSocketHandler webSocketHandler) {
        this.myInterceptor = myInterceptor;
        this.webSocketHandler = webSocketHandler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry//ws url
                .addHandler(webSocketHandler, "ws")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}
