package io.github.franzli347.foss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.SysUser;
import io.github.franzli347.foss.mapper.UserMapper;
import io.github.franzli347.foss.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Author AlanC
 * @Date 2023/1/8 16:14
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {
    QueryWrapper<SysUser> wrapper=new QueryWrapper<>();

    @Override
    public SysUser getUserByUsername(String username) {
        return baseMapper.selectOne(wrapper.eq("username",username));
    }
}
