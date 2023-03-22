package io.github.franzli347.foss.listener;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import eu.bitwalker.useragentutils.UserAgent;
import io.github.franzli347.foss.vo.LoginRecord;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 自定义侦听器的实现
 */
@Component
@Slf4j
public class MySaTokenListener implements SaTokenListener {
    private final UserService service;
    private final RestTemplate restTemplate;
    public MySaTokenListener(UserService service,RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        HttpServletRequest request=(HttpServletRequest)SaHolder.getRequest().getSource();
        String ip=IPUtil.getIpAddress(request);
       service.addLoginRecord(LoginRecord.builder()
               .ip(ip)
               .time(LocalDateTime.now())
               .uid((Integer) loginId)
               .device(UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem().getName())
               .city(IPUtil.getCityByIp(restTemplate,ip))
               .build());

    }
    @Override
    public void doLogout(String s, Object o, String s1) {

    }

    @Override
    public void doKickout(String s, Object o, String s1) {

    }

    @Override
    public void doReplaced(String s, Object o, String s1) {

    }

    @Override
    public void doDisable(String s, Object o, String s1, int i, long l) {

    }

    @Override
    public void doUntieDisable(String s, Object o, String s1) {

    }

    @Override
    public void doOpenSafe(String s, String s1, String s2, long l) {

    }

    @Override
    public void doCloseSafe(String s, String s1, String s2) {

    }

    @Override
    public void doCreateSession(String s) {

    }

    @Override
    public void doLogoutSession(String s) {

    }

    @Override
    public void doRenewTimeout(String s, Object o, long l) {

    }


}
