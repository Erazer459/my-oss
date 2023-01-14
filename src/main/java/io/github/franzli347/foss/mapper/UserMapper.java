package io.github.franzli347.foss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.vo.LoginRecord;
import io.github.franzli347.foss.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    @Select("select * from tb_sysuser where username = #{username}")
    SysUser getUserByUsername(String username);
    @Insert("insert into tb_login_record (ip,time,uid,city) values(#{ip},#{time},#{uid},#{city})")
    void addLoginRecord(LoginRecord record);
    @Select("select * from tb_login_record where uid=#{uid} order by time desc limit ${page},${size}")
    List<LoginRecord> getUserLoginRecords(int uid,int page,int size);
}
