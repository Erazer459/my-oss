package io.github.franzli347.foss.support.wsSupport;

/**
 * @ClassName WsSessionManager
 * @Author AlanC
 * @Date 2022/12/29 17:55
 **/
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.RedisConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;


/**
 * @Author AlanC
 * @Description  WsSessionManager
 * @Date 15:12 2022/12/30
 * @Param
 * @return
 **/
@Slf4j
public class WsSessionProvider {
    private static StringRedisTemplate stringRedisTemplate;
    static ObjectMapper objectMapper;
    /**
     * 保存连接 session 的地方
     */

    public WsSessionProvider(StringRedisTemplate stringRedisTemplate) {
        WsSessionProvider.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加 session
     *
     * @param key
     */
    @SneakyThrows
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        stringRedisTemplate.opsForValue().set(RedisConstant.WS_SESSION+"_"+key, objectMapper.writeValueAsString(session));
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static Boolean remove(String key) {
        // 删除 session
        return stringRedisTemplate.delete(RedisConstant.WS_SESSION+"_"+key);
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        if (Optional.ofNullable(stringRedisTemplate.delete(key)).orElse(false)) {
            try {
                // 关闭连接
                objectMapper.readValue(stringRedisTemplate.opsForValue().get(key),WebSocketSession.class).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    @SneakyThrows
    public static WebSocketSession get(String key) {
        // 获得 session
        return objectMapper.readValue(stringRedisTemplate.opsForValue().get(key),WebSocketSession.class);
    }
}