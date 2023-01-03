package io.github.franzli347.foss.support.wsSupport;

/**
 * @ClassName WsSessionManager
 * @Author AlanC
 * @Date 2022/12/29 17:55
 **/

import io.github.franzli347.foss.common.RedisConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author AlanC
 * @Description  WsSessionManager
 * @Date 15:12 2022/12/30
 * @Param
 * @return
 **/
@Slf4j
@Component
public class WsSessionProvider {
    private final StringRedisTemplate stringRedisTemplate;
    public WsSessionProvider(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 保存连接 session
     */
    private final static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();//wsSession没有实现Serializable


    /**
     * 添加 session
     *
     * @param key
     * @param session
     */
    @SneakyThrows
    public void add(String key, WebSocketSession session) {
        // 添加 session
        stringRedisTemplate.opsForSet().add(RedisConstant.WS_SESSION_LIST,key);
        SESSION_POOL.put(key,session);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public  WebSocketSession remove(String key) {
        // 删除 session
        stringRedisTemplate.opsForSet().remove(RedisConstant.WS_SESSION_LIST,key);
        return SESSION_POOL.remove(key);

    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    @SneakyThrows
    public  void removeAndClose(String key) {
        stringRedisTemplate.opsForSet().remove(RedisConstant.WS_SESSION_LIST,key);
        SESSION_POOL.get(key).close(CloseStatus.NORMAL);
        SESSION_POOL.remove(key);
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    @SneakyThrows
    public  WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }
}