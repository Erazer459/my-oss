package io.github.franzli347.foss.utils;

import cn.dev33.satoken.context.model.SaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.vo.IpVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName IPUtil
 * @Author AlanC
 * @Date 2023/1/13 21:01
 **/
@Slf4j
public class IPUtil {
    public static String getIpAddress(SaRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
        return ip;
    }
    @SneakyThrows
    public static IpVo getCityByIp(RestTemplate restTemplate, ObjectMapper objectMapper, String ip){
        String info=restTemplate.getForObject("http://ip-api.com/json/{ip}?lang=zh-CN", String.class,ip);
        return objectMapper.readValue(info, IpVo.class);
    }
}
