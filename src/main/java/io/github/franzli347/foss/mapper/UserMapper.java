package io.github.franzli347.foss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

}
