package io.github.franzli347.foss.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.entity.AuthKeys;
import io.github.franzli347.foss.service.ShareAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @ClassName ShareAuthController
 * @Author AlanC
 * @Date 2023/3/27 20:29
 **/
@RestController
@RequestMapping("/share")
@Tag(name="分享链接模块")
public class ShareAuthController {
    private final ShareAuthService service;

    public ShareAuthController(ShareAuthService service) {
        this.service = service;
    }
    @GetMapping("/getKeys")
    @Operation(summary = "获取当前用户的secretkey和accesskey")
    public AuthKeys getKeys(){
        return service.getKeys();
    }
    @PostMapping("/updateSecretKey")
    @Operation(summary = "更新当前用户的secretKey,返回值为新的secretkey")
    public String updateSecretKey(){
        String s = UUID.randomUUID().toString();
        service.update(AuthKeys.builder().secretkey(s).build(),new LambdaUpdateWrapper<AuthKeys>().eq(AuthKeys::getUid,StpUtil.getLoginIdAsInt()));
        return s;
    }
    @GetMapping("/generateSharePath")
    @Operation(summary = "生成文件分享链接,图片可选择是展示还是下载(图床or下载)")
    @Parameter(name = "expire",description = "过期时间,单位为分钟")
    @Parameter(name = "fileId",description = "文件ID")
    public String generateSharePath(int expire,int fileId){
        return service.generateSharePath(expire,fileId);
    }
}
