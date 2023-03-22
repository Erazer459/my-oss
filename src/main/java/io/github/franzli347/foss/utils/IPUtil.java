package io.github.franzli347.foss.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IPUtil
 * @Author AlanC
 * @Date 2023/1/13 21:01
 **/
@Slf4j
public class IPUtil {
    public static String getIpAddress(HttpServletRequest request) {
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
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    @SneakyThrows
    public static String getCityByIp(RestTemplate restTemplate, String ip){
        String sig= SaSecureUtil.md5("/ws/location/v1/ip?ip="+ip+"&key=OIDBZ-XC734-NXTUU-FRY4H-KJRJK-3JB34SBBEIitwl5d1VFxmuz9qAXYJcP3SGCw");
        return  restTemplate.getForObject("https://apis.map.qq.com/ws/location/v1/ip?ip="+ip+"&key=OIDBZ-XC734-NXTUU-FRY4H-KJRJK-3JB34&sig="+sig, String.class,ip);
    }
}
