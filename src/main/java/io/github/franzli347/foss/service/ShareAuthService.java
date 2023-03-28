package io.github.franzli347.foss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.entity.AuthKeys;


public interface ShareAuthService extends IService<AuthKeys> {
    AuthKeys getKeys();

    void generateKeys(int uid);

    String generateSharePath(int expire, int fileId);
}
