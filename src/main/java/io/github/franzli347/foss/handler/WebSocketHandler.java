package io.github.franzli347.foss.handler;

import com.alibaba.fastjson.JSON;
import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.support.wsSupport.WsSessionProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

/**
 * @ClassName WebSocketHandler
 * @Author AlanC
 * @Date 2022/12/29 16:26
 **/
@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final StringRedisTemplate stringRedisTemplate;

    public WebSocketHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object userId = session.getAttributes().get("id");
        if (userId != null) {
            // 用户连接成功，放入redis
            WsSessionProvider.add(userId.toString(), session);
        } else {
            throw new RuntimeException("用户验证失败!");
        }
    }
    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object userId = session.getAttributes().get("userId");
        log.info("server 接收到 {} 发送的 {} ",userId,payload);
        session.sendMessage(new TextMessage("server 发送给 " + userId + " 消息 " + payload + " " + LocalDateTime.now().toString()));
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object userId = session.getAttributes().get("userId");
        if (userId != null) {
            // 用户退出，移除缓存
            WsSessionProvider.removeAndClose(userId.toString());
        }
    }
    @SneakyThrows
    public <T> void sendPercentageMsg(String userId,ProcessInfo info){
        log.info("向客户端发送文件任务进度信息");
        WebSocketSession webSocketSession = WsSessionProvider.get(userId);
        webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(Result.builder().code(200).msg("任务进度").data(info).build())));
    }
    @SneakyThrows
    public <T> void sendResultMsg(String userId, Result result){
        log.info("发送ws消息");
        WebSocketSession webSocketSession= WsSessionProvider.get(userId);
        webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(result)));
    }

}
