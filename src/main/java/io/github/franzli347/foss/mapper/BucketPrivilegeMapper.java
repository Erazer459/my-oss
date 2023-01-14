package io.github.franzli347.foss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.entity.BucketPrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BucketPrivilegeMapper extends BaseMapper<BucketPrivilege> {
    @Update("update tb_bucket_privilege set privilege=#{privilege} where id=#{privilegeId}")
    int updatePrivilege(int privilegeId, String privilege);
}
