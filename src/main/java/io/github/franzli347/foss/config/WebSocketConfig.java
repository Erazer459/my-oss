package io.github.franzli347.foss.config;
import io.github.franzli347.foss.Interceptor.MyhandshakeInterceptor;
import io.github.franzli347.foss.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @ClassName WebSocketConfig
 * @Author AlanC
 * @Date 2022/12/29 15:09
 * @Description websocket配置类
 **/
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler httpAuthHandler;
    private final MyhandshakeInterceptor myInterceptor;

    public WebSocketConfig(MyhandshakeInterceptor myInterceptor, WebSocketHandler httpAuthHandler) {
        this.myInterceptor = myInterceptor;
        this.httpAuthHandler = httpAuthHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "/websocket")//前端new websocket的url
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");

    }
}
