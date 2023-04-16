package io.github.franzli347.foss.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.model.entity.BackupTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackupTaskMapper extends BaseMapper<BackupTask> {
}
