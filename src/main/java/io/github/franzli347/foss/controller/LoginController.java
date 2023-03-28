package io.github.franzli347.foss.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.service.ShareAuthService;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.utils.EncryptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@Tag(name = "登录接口")
@Slf4j
public class LoginController {
    private final UserService service;
    private final ShareAuthService authService;
    public LoginController(UserService service, ShareAuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping("/registry")
    @Operation(summary = "用户注册")
    @SneakyThrows
    public Result registry(@RequestBody SysUser user){
        Optional.ofNullable(service.getUserByUsername(user.getUsername())).ifPresent(r-> {
            throw new RuntimeException("用户名已存在");
        });
        Optional.ofNullable(service.getUserByEmail(user.getEmail())).ifPresent(r->{
        throw new RuntimeException("邮箱已被使用");
        });
        // sha1加密
        String password = EncryptionUtil.getEncryptedPassword(user.getPassword());
        user.setPassword(password);
        service.save(user);
        authService.generateKeys(user.getId());
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("注册成功").build();
    }
    @SneakyThrows
    @PostMapping("/doLogin")
    @Operation(summary = "用户登录")
    public Result doLogin(@RequestBody SysUser user){
        SysUser sysUser;
        if (Optional.ofNullable(user.getEmail()).isPresent()){
            sysUser=Optional.ofNullable(service.getUserByEmail(user.getEmail())).orElseThrow(()->new RuntimeException("邮箱错误或用户不存在"));
        }else {
            sysUser=Optional.ofNullable(service.getUserByUsername(user.getUsername())).orElseThrow(()->new RuntimeException("用户名错误或用户不存在"));
        }
        if (!EncryptionUtil.authenticate(user.getPassword(),sysUser.getPassword())){
            throw new RuntimeException("密码错误");
        }
        if (StringUtils.isNotBlank(StpUtil.getTokenValueByLoginId(sysUser.getId()))){
            StpUtil.logout(sysUser.getId());//如果token不为空就先logout
        }
        StpUtil.login(sysUser.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();//回传token信息
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("登录成功").data(tokenInfo.getTokenValue()).build();
    }
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public Result logout(){
        StpUtil.logout(StpUtil.getLoginId());
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("用户登出成功").build();
    }
}
