package io.github.franzli347.foss.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.model.entity.SysUser;
import io.github.franzli347.foss.web.mapper.UserMapper;
import io.github.franzli347.foss.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author AlanC
 * @Date 2023/1/8 16:14
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {


    @Override
    public SysUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
    }

    @Override
    public void addLoginRecord(LoginRecord record) {
        baseMapper.addLoginRecord(record);
    }

    @Override
    public List<LoginRecord> getLoginRecord(int uid,int page,int size) {
       return baseMapper.getUserLoginRecords(uid,page,size);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail,email));
    }


}
