package io.github.franzli347.foss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.vo.LoginRecord;
import io.github.franzli347.foss.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    void addLoginRecord(LoginRecord record);
    List<LoginRecord> getUserLoginRecords(int uid,int page,int size);
}
