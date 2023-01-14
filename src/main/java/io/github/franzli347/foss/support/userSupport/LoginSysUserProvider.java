package io.github.franzli347.foss.support.userSupport;

import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.service.UserService;

import javax.annotation.Resource;

public class LoginSysUserProvider implements LoginUserProvider{
    @Resource
    UserService userService;

    @Override
    public SysUser getLoginUser() {
        return userService.getById(StpUtil.getLoginIdAsInt());
    }


}
