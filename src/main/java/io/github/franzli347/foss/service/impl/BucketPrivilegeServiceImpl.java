package io.github.franzli347.foss.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.BucketPrivilege;
import io.github.franzli347.foss.mapper.BucketPrivilegeMapper;
import io.github.franzli347.foss.service.BucketPrivilegeService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @ClassName BucketPrivilegeServiceImpl
 * @Author AlanC
 * @Date 2023/1/11 22:04
 **/
@Service
public class BucketPrivilegeServiceImpl extends ServiceImpl<BucketPrivilegeMapper, BucketPrivilege> implements BucketPrivilegeService {
    private final LoginUserProvider loginUserProvider;

    public BucketPrivilegeServiceImpl(LoginUserProvider loginUserProvider) {
        this.loginUserProvider = loginUserProvider;
    }

    /**
     * @Author AlanC
     * @Description 获取登录用户所拥有的所有bucket权限信息
     * @Date 22:39 2023/1/11
     * @Param [userId]
     * @return
     **/
    @Override
    public List<Map<String, Object>> getPrivilegeAsMap(int userId) {
        return baseMapper.selectMaps(new LambdaQueryWrapper<BucketPrivilege>().select(BucketPrivilege::getBid,BucketPrivilege::getPrivilege).eq(BucketPrivilege::getUid,userId));
    }
    @Override
    public void checkPrivilegeExist(BucketPrivilege privilege) {
        Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<BucketPrivilege>()
                .select(BucketPrivilege::getPrivilege)
                .eq(BucketPrivilege::getUid, privilege.getUid())
                .eq(BucketPrivilege::getBid, privilege.getBid()))).ifPresent(r->{
            throw new RuntimeException("该条授权信息已存在");
        });
    }

    @Override
    public List<BucketPrivilege> getBucketPrivilegeByBid(int bid, String type, int page, int size) {
        return baseMapper.getBucketPrivilegeByBid(bid,type,page,size);
    }

    @Override
    public List<BucketPrivilege> getAllPrivilegeInfo(int id) {
        return baseMapper.selectList(new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getUid,id));
    }

    @Override
    public boolean updatePrivilege(int privilegeId, String privilege) {
        return baseMapper.updatePrivilege(privilegeId,privilege);
    }


}