package io.github.franzli347.foss.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.model.entity.SysUser;

import java.util.List;

public interface UserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);

    void addLoginRecord(LoginRecord record);

    List<LoginRecord> getLoginRecord(int uid,int page,int size);
    SysUser getUserByEmail(String email);

}
