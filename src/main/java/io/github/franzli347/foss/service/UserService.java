package io.github.franzli347.foss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.entity.SysUser;

public interface UserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);
}
