package io.github.franzli347.foss.listener;

/**
 * @ClassName MySaTokenListener
 * @Author AlanC
 * @Date 2023/1/9 17:12
 **/

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import org.springframework.stereotype.Component;

/**
 * 自定义侦听器的实现
 */
@Component
public class MySaTokenListener implements SaTokenListener {
    private final LoginUserProvider loginUserProvider;
    private final UserService service;
    public MySaTokenListener(LoginUserProvider loginUserProvider, UserService service) {
        this.loginUserProvider = loginUserProvider;
        this.service = service;
    }

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        loginUserProvider.setLoginUser(service.getById(loginId.toString()));
    }

    @Override
    public void doLogout(String s, Object o, String s1) {
        loginUserProvider.setLoginUser(new SysUser());
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
