package io.github.franzli347.foss.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.model.entity.SysUser;
import io.github.franzli347.foss.utils.IPUtil;
import io.github.franzli347.foss.web.mapper.UserMapper;
import io.github.franzli347.foss.web.service.LoginRecordService;
import io.github.franzli347.foss.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @ClassName UserServiceImpl
 * @Author AlanC
 * @Date 2023/1/8 16:14
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {
    private final LoginRecordService loginRecordService;
    private final RestTemplate restTemplate;
    public UserServiceImpl(LoginRecordService loginRecordService, RestTemplate restTemplate) {
        this.loginRecordService = loginRecordService;
        this.restTemplate = restTemplate;
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
    }

    @Override
    public void addLoginRecord(LoginRecord record) {
        baseMapper.addLoginRecord(record);
    }

    @Override
    public IPage<LoginRecord> getLoginRecord(int uid, int page, int size) {
        IPage<LoginRecord> p=new Page<>(page,size);
        loginRecordService.page(p,new LambdaQueryWrapper<LoginRecord>().eq(LoginRecord::getUid,uid));
        return p;
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail,email));
    }

    @Override
    @Async("asyncExecutor")
    public void getIPInfo(HttpServletRequest request,int loginId) {
        String ip= IPUtil.getIpAddress(request);
        addLoginRecord(LoginRecord.builder()
                .ip(ip)
                .time(LocalDateTime.now())
                .uid(loginId)
                .device(UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem().getName())
                .city(IPUtil.getCityByIp(restTemplate,ip))
                .build());
        log.info("异步任务执行完毕");
    }


}
