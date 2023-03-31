package io.github.franzli347.foss.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.entity.AuthKeys;


public interface ShareAuthService extends IService<AuthKeys> {
    AuthKeys getKeys();

    void generateKeys(int uid);

    String generateSharePath(int bid,int expire, String fileId);
}
