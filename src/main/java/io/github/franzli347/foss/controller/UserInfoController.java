package io.github.franzli347.foss.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.github.franzli347.foss.utils.EncryptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName UserInfoController
 * @Author AlanC
 * @Date 2023/1/13 20:50
 **/
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息接口")
public class UserInfoController {
    private final UserService userService;

    private final LoginUserProvider loginUserProvider;

    public UserInfoController(UserService userService, LoginUserProvider loginUserProvider) {
        this.userService = userService;
        this.loginUserProvider = loginUserProvider;
    }

    @GetMapping("/getLoginRecord/{page}/{size}")
    @Operation(summary = "获取当前用户的登陆记录")
    @Parameter(name = "page", description = "页码")
    @Parameter(name = "size", description = "每页数量")
    public Result getLoginRecord(@PathVariable int page,@PathVariable int size){
       return Result.builder()
               .code(ResultCode.CODE_SUCCESS)
               .data(userService.getLoginRecord(loginUserProvider.getLoginUser().getId(),page,size))
               .build();
    }
    @GetMapping("/get")
    @Operation(summary = "获取当前用户信息(id,用户名,邮箱)")
    public Result get(){
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(userService.getById(loginUserProvider.getLoginUser().getId()).toUserBase())
                .build();
    }
    @PostMapping("/update")
    @Operation(summary = "更新当前用户信息(用户名或邮箱)")
    @Parameter(name = "username",description = "更新后的用户名",required = false)
    @Parameter(name = "email",description = "更新后的邮箱地址",required = false)
    public Result update(@RequestParam(required = false) String username,@RequestParam(required = false) String email){
        Optional.ofNullable(userService.getUserByUsername(username)).ifPresent(s -> {throw new RuntimeException("该用户名已存在");});
        Optional.ofNullable(userService.getUserByEmail(email)).ifPresent(r->{
            throw new RuntimeException("邮箱已被使用");
        });
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(userService.updateById(SysUser.builder()
                        .id(loginUserProvider.getLoginUser().getId())
                        .email(email)
                        .username(username).build()))
                .build();
    }
    @SneakyThrows
    @PostMapping("/update/password")
    @Operation(summary = "更新当前用户密码")
    public Result updatePassword(String oldPassword,String newPassword){
       if (!EncryptionUtil.authenticate(oldPassword,loginUserProvider.getLoginUser().getPassword(),loginUserProvider.getLoginUser().getSalt())){
           throw new RuntimeException("原密码错误");
       }
       String newSalt = EncryptionUtil.generateSalt();
       newPassword=EncryptionUtil.getEncryptedPassword(newPassword,newSalt);
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(userService.updateById(SysUser.builder()
                        .id(loginUserProvider.getLoginUser().getId())
                        .salt(newSalt)
                        .password(newPassword)
                        .build()))
                .build();
    }
}
