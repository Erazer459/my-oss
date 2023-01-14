package io.github.franzli347.foss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.entity.BucketPrivilege;

import java.util.List;
import java.util.Map;

/**
*@ClassName BucketPrivilegeService
*@Author AlanC
*@Date 2023/1/11 21:59
**/
public interface BucketPrivilegeService extends IService<BucketPrivilege> {
    List<Map<String,Object>> getPrivilegeAsMap(int userId);

    int setPrivilege(BucketPrivilege privilege);

    void checkPrivilegeExist(BucketPrivilege privilege);
    
    List<BucketPrivilege> getBucketPrivilegeByBid(int bid);

    List<BucketPrivilege> getAllPrivilegeInfo(int id);

    int updatePrivilege(int privilegeId, String privilege);
}
