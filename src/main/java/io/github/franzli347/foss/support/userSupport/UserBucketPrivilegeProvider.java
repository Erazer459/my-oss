package io.github.franzli347.foss.support.userSupport;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.common.constant.AuthConstant;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserAuthProvider
 * @Author AlanC
 * @Date 2023/1/9 21:51
 **/
@Component
@Slf4j
public class UserBucketPrivilegeProvider implements StpInterface {
    private final BucketPrivilegeService bucketPrivilegeService;
    private final BucketService bucketService;
    private final LoginUserProvider loginUserProvider;

    public UserBucketPrivilegeProvider(BucketPrivilegeService bucketPrivilegeService, BucketService bucketService, LoginUserProvider loginUserProvider) {
        this.bucketPrivilegeService = bucketPrivilegeService;
        this.bucketService = bucketService;
        this.loginUserProvider = loginUserProvider;
    }

    /**
     * 返回一个账号所拥有的Bucket的权限码集合(BUKET权限表)
     * “bid-r or bid-rw or bid-rw-owner”
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Map<String, Object>> privilegeList = bucketPrivilegeService.getPrivilegeAsMap(StpUtil.getLoginIdAsInt());
        // privilegeList [[bid,"49",prvili,"rw"]]
        if (privilegeList.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        privilegeList.forEach(map -> {
            log.info(map.get("bid").toString());
            if (bucketService.getById((Serializable) map.get("bid")).getUid() == loginUserProvider.getLoginUser().getId()) {
                list.add(AuthUtil.bidConcat(map.get("bid"), AuthConstant.OWNER));
            } else {
                list.add(AuthUtil.bidConcat(map.get("bid"), map.get("privilege")));
            }
        });

        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>();
    }
}
