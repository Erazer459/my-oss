package io.github.franzli347.foss.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.AuthKeys;
import io.github.franzli347.foss.mapper.AuthKeysMapper;
import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.service.ShareAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName ShareAuthServiceImpl
 * @Author AlanC
 * @Date 2023/3/27 21:03
 **/
@Service
public class ShareAuthServiceImpl extends ServiceImpl<AuthKeysMapper,AuthKeys> implements ShareAuthService {
    @Value("${downloadAddr}")
    private String domain;

    private final FilesService filesService;

    public ShareAuthServiceImpl(FilesService filesService) {
        this.filesService = filesService;
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
    public String generateSharePath(int expire, int fileId) {
        String fileName=filesService.getById(fileId).getFileName();
        AuthKeys authKeys = baseMapper.selectOne(new LambdaUpdateWrapper<AuthKeys>().eq(AuthKeys::getUid, StpUtil.getLoginIdAsInt()));
        String sign= SaSecureUtil.sha1(authKeys.getAccesskey()+authKeys.getSecretkey()+expire+fileName);
        return domain+"/[%s]?accesskey=[%s]&expire=[%d]&fileName=[%s]&[%s]".formatted(fileName,authKeys.getAccesskey(),expire,fileName,sign);
    }
}
