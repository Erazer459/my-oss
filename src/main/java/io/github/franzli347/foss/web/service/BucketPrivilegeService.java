package io.github.franzli347.foss.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.entity.BucketPrivilege;

import java.util.List;
import java.util.Map;

/**
*@ClassName BucketPrivilegeService
*@Author AlanC
*@Date 2023/1/11 21:59
**/
public interface BucketPrivilegeService extends IService<BucketPrivilege> {
    List<Map<String,Object>> getPrivilegeAsMap(int userId);

    void checkPrivilegeExist(BucketPrivilege privilege);
    
   IPage<BucketPrivilege> getBucketPrivilegeByBid(int bid, String type, int page, int size);

    List<BucketPrivilege> getAllPrivilegeInfo(int id);

    boolean updatePrivilege(int privilegeId, String privilege);
}
