package io.github.franzli347.foss.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.entity.BackupTask;

public interface BackupTaskService extends IService<BackupTask> {
    long init(BackupTask task);

    void recover(long snapshotId,int uid);
}
