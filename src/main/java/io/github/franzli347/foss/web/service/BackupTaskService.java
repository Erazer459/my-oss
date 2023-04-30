package io.github.franzli347.foss.web.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.entity.BackupSnapshot;
import io.github.franzli347.foss.model.entity.BackupTask;

public interface BackupTaskService extends IService<BackupTask> {
    String init(BackupTask task);

    void recover(String snapshotId,int uid);

    IPage<BackupSnapshot> getSnapshots(int bid, int page, int size);

    boolean stop(String taskId);

    boolean updateTask(BackupTask task);

    boolean deleteTask(String taskId);

    boolean resumeTask(String taskId);

    boolean executeTask(String taskId);

    String getStatus(String taskId);
}
