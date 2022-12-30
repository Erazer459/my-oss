package io.github.franzli347.foss.handler;

import com.alibaba.fastjson.JSON;
import io.github.franzli347.foss.common.ProcessInfo;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.utils.WsSessionManager;
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
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(token.toString(), session);
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
        Object token = session.getAttributes().get("token");
        log.info("server 接收到 {} 发送的 {} ",token,payload );
        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " + LocalDateTime.now().toString()));
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
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(token.toString());
        }
    }
    @SneakyThrows
    public <T> void sendPercentageMsg(String token,ProcessInfo info){
        log.info("向客户端发送文件任务信息");
        WebSocketSession webSocketSession = WsSessionManager.get(token);
        webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(Result.builder().code(200).data(info).build())));
        if(info.isDone()){
            webSocketSession.close(new CloseStatus(200,"文件任务完成"));
        }
    }

}
