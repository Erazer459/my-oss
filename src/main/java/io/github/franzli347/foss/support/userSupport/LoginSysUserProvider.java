package io.github.franzli347.foss.support.userSupport;

import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.entity.UserBase;
import io.github.franzli347.foss.service.UserService;

public class LoginSysUserProvider implements LoginUserProvider{

    private SysUser curruntUser;

    @Override
    public SysUser getLoginUser() {
        return curruntUser;
    }

    @Override
    public void setLoginUser(SysUser sysUser) {
        this.curruntUser=sysUser;
    }

}
