package io.github.franzli347.foss.support.userSupport;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserAuthProvider
 * @Author AlanC
 * @Date 2023/1/9 21:51
 **/
@Component
public class UserAuthProvider implements StpInterface {
    /**
     * 返回一个账号所拥有的Bucket的权限码集合
     * “BUKET-bid-READ”
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }
    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>();
    }
}
