package io.github.franzli347.foss.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.model.entity.BucketPrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BucketPrivilegeMapper extends BaseMapper<BucketPrivilege> {
    boolean updatePrivilege(int id, String privilege);
    List<BucketPrivilege> getBucketPrivilegeByBid(int bid,String type,int page,int size);
}
