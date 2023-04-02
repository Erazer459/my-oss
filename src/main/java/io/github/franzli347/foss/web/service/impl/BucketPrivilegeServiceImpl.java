package io.github.franzli347.foss.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.entity.BucketPrivilege;
import io.github.franzli347.foss.model.vo.PrivilegeVo;
import io.github.franzli347.foss.web.mapper.BucketPrivilegeMapper;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final UserService userService;

    public BucketPrivilegeServiceImpl(UserService userService) {
        this.userService = userService;
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
    public IPage<PrivilegeVo> getBucketPrivilegeByBid(int bid, String type, int page, int size) {
        IPage<BucketPrivilege> p=new Page<>(page,size);
        if (type==null){
            page(p,new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid,bid));
        }
        else {
            page(p,new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid,bid).eq(BucketPrivilege::getPrivilege,type));
        }
        IPage<PrivilegeVo> privilegeVoIPage=new Page<>();
        List<PrivilegeVo> records=new ArrayList<>();
        List<BucketPrivilege> bpr=p.getRecords();
        for (int i=0;i<p.getRecords().size();i++){
            BucketPrivilege privilege = bpr.get(i);
            records.add(PrivilegeVo.builder().id(privilege.getId())
                            .bid(privilege.getBid())
                            .uid(privilege.getUid())
                            .createTime(privilege.getCreateTime())
                            .updateTime(privilege.getUpdateTime())
                            .privilege(privilege.getPrivilege())
                            .username(userService.getById(privilege.getUid()).getUsername())
                    .build());
        }
        privilegeVoIPage.setPages(p.getPages());
        privilegeVoIPage.setCurrent(p.getCurrent());
        privilegeVoIPage.setRecords(records);
        privilegeVoIPage.setTotal(p.getTotal());
        privilegeVoIPage.setSize(p.getSize());
        return privilegeVoIPage;
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
