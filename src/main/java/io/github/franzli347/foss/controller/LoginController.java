package io.github.franzli347.foss.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.exception.BaseException;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.utils.EncryptionUtil;
import io.github.franzli347.foss.utils.RSAUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@Tag(name = "登录接口")
@Slf4j
public class LoginController {
    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    @PostMapping("/registry")
    @Operation(summary = "用户注册")
    @SneakyThrows
    public Result registry(@RequestBody SysUser user){
        if (service.getUserByUsername(user.getUsername())!=null){
            throw new BaseException("用户已存在");
        }
        String password = RSAUtil.encrypt(user.getPassword());// 密码公钥加密
        String salt = EncryptionUtil.generateSalt();
        // 盐值加密
        password = EncryptionUtil.getEncryptedPassword(password, salt);
        user.setPassword(password);
        user.setSalt(salt);
        service.save(user);
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("注册成功").build();//koji 123
    }
    @SneakyThrows
    @PostMapping("/doLogin")
    @Operation(summary = "用户登录")
    public Result doLogin(@RequestBody SysUser loginUser){
        SysUser sysUser=Optional.ofNullable(service.getUserByUsername(loginUser.getUsername())).orElseThrow(()->new BaseException("用户不存在"));
        Optional.of(EncryptionUtil.authenticate(loginUser.getPassword(),sysUser.getPassword(),sysUser.getSalt())).orElseThrow(()->new BaseException("用户名或密码错误"));
        String oldToken= StpUtil.getTokenValueByLoginId(sysUser.getId());
        if (StringUtils.isNotBlank(oldToken)){
            StpUtil.logout(sysUser.getId());//如果token不为空就先logout
        }
        StpUtil.login(sysUser.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();//回传token信息
        return Result.builder().code(ResultCode.CODE_SUCCESS).msg("登录成功").data(tokenInfo).build();
    }
}
