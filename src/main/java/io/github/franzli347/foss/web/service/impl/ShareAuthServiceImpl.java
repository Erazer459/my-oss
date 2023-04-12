package io.github.franzli347.foss.web.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.entity.AuthKeys;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.web.mapper.AuthKeysMapper;
import io.github.franzli347.foss.web.service.FilesService;
import io.github.franzli347.foss.web.service.ShareAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName ShareAuthServiceImpl
 * @Author AlanC
 * @Date 2023/3/27 21:03
 **/
@Service
@Slf4j
@Validated
public class ShareAuthServiceImpl extends ServiceImpl<AuthKeysMapper,AuthKeys> implements ShareAuthService {
    @Value("${downloadAddr}")
    private String domain;

    private final FilesService filesService;
    private final StringRedisTemplate template;
    public ShareAuthServiceImpl(FilesService filesService, StringRedisTemplate template) {
        this.filesService = filesService;
        this.template = template;
    }

    @Override
    public AuthKeys getKeys() {
        return baseMapper.selectOne(new LambdaQueryWrapper<AuthKeys>().eq(AuthKeys::getUid, StpUtil.getLoginIdAsInt()));
    }
    @Override
    public void generateKeys(int uid) {
        baseMapper.insert(AuthKeys.builder().accesskey(UUID.randomUUID().toString()).secretkey(UUID.randomUUID().toString()).uid(uid).build());
    }

    @Override
    public String generateSharePath(int bid, int expire, String fileId) {//lua拿着链接判断是否过期，没有过期则拿到用户id然后去拿secretkey,然后解密,最后下载
        long timestamp = System.currentTimeMillis() / 1000 + expire * 60L;
        Files file=Optional.ofNullable(filesService.getById(fileId)).orElseThrow(()->new RuntimeException("文件不存在"));
        AuthKeys authKeys = baseMapper.selectOne(new LambdaQueryWrapper<AuthKeys>().eq(AuthKeys::getUid, StpUtil.getLoginIdAsInt()));
        String sign= SaSecureUtil.sha1(authKeys.getAccesskey()+authKeys.getSecretkey()+timestamp);
        String sharePath=domain+"/download/%s?accesskey=%s&expire=%d&fileName=%s&bid=%d&sign=%s".formatted(file.getFileName(),authKeys.getAccesskey(),timestamp,file.getFileName(),bid,sign);
        return sharePath;
    }
}
