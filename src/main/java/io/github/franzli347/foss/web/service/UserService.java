package io.github.franzli347.foss.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.vo.BucketWithPriviledge;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.model.entity.SysUser;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);

    void addLoginRecord(LoginRecord record);

    IPage<LoginRecord> getLoginRecord(int uid, int page, int size);
    SysUser getUserByEmail(String email);
    @Async("asyncExecutor")
    void getIPInfo(HttpServletRequest request,int loginId);
}
