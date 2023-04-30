package io.github.franzli347.foss.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.middleware.manager.QuartzManage;
import io.github.franzli347.foss.model.entity.*;
import io.github.franzli347.foss.job.QuartzJob;
import io.github.franzli347.foss.protobuf.FileTransferClient;
import io.github.franzli347.foss.web.mapper.BackupTaskMapper;
import io.github.franzli347.foss.web.service.BackupTaskService;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.web.service.FilesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BackupTaskServiceImpl
 * @Author AlanC
 * @Date 2023/4/14 16:26
 **/
@Service
@Slf4j
public class BackupTaskServiceImpl extends ServiceImpl<BackupTaskMapper, BackupTask> implements BackupTaskService {
    private final QuartzManage manage;
    private final MongoTemplate mongoTemplate;
    private final BucketService bucketService;
    private final BucketPrivilegeService privilegeService;
    private final FilesService filesService;
    private final StringRedisTemplate redisTemplate;
    @Value("${gRPC.backupPath}")
    private  String snapshotPath;
    @Value("${pathMap.source}")
    private  String filePath;
    @Value("${gRPC.Server.HOST}")
    private  String host;
    @Value("${gRPC.Server.Port}")
    private  int port;
    public BackupTaskServiceImpl(QuartzManage manage, MongoTemplate mongoTemplate, BucketService bucketService, BucketPrivilegeService privilegeService, FilesService filesService, StringRedisTemplate redisTemplate) {
        this.manage = manage;
        this.mongoTemplate = mongoTemplate;
        this.bucketService = bucketService;
        this.privilegeService = privilegeService;
        this.filesService = filesService;
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public String init(BackupTask task) {
        String id = task.getId();
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+id,RedisConstant.BACKUP_TASK_READY);
        manage.addJob(QuartzJob.builder()
                .jobId(id)
                .jobName(task.getTaskName())
                .triggerName(task.getId())
                .methodName("run")
                .timeGap(task.getTimeGap())
                .startTime(task.getStartTime())
                        .timeGap(task.getTimeGap())
                .bid(task.getBid()).build());
        return id;
    }

    @SneakyThrows
    @Override
    @Async("asyncExecutor")
    @Transactional
    public void recover(String snapshotId,int uid) {
    Optional.ofNullable(redisTemplate.opsForValue().get(snapshotId)).ifPresent(r->{
    throw new RuntimeException("备份任务已在运行");
    });
        redisTemplate.opsForValue().set(RedisConstant.RECOVER_TASK+":"+snapshotId,RedisConstant.RECOVER_TASK_RUNNING);
        redisTemplate.expire(RedisConstant.RECOVER_TASK+":"+snapshotId,RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
        BackupSnapshot snapshot=Optional.ofNullable(mongoTemplate.findOne(new Query(Criteria.where("_id").is(snapshotId)),BackupSnapshot.class)).orElseThrow(()->new RuntimeException("不存在此备份快照信息"));
        Integer bid = snapshot.getBucketInfo().getId();
        bucketService.update(snapshot.getBucketInfo(),new LambdaUpdateWrapper<Bucket>().eq(Bucket::getId,bid));
        for(Files file:filesService.list(new LambdaQueryWrapper<Files>().eq(Files::getBid,bid))){//清除旧记录
            filesService.removeFilesById(file.getId(), String.valueOf(file.getBid()));
        }
        for (BucketPrivilege privilege:privilegeService.list(new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid,bid))){
            privilegeService.removeById(privilege.getId());
        }
        privilegeService.saveBatch(snapshot.getPrivilegeInfos());
        filesService.saveBatch(snapshot.getFilesInfo());
        FileTransferClient fileTransferClient = new FileTransferClient(host,port);//开启gRPC客户端接收文件
        for(Files file:snapshot.getFilesInfo()){//开始恢复文件
            fileTransferClient.download(snapshotPath+snapshotId+"/"+file.getFileName(),filePath+ file.getBid()+"/"+file.getFileName());
        }
        fileTransferClient.shutdown();
        redisTemplate.opsForValue().getOperations().delete(RedisConstant.RECOVER_TASK+":"+snapshotId);
    }

    @Override
    public IPage<BackupSnapshot> getSnapshots(int bid, int page, int size) {
        List<BackupSnapshot> snapshots = mongoTemplate.find(new Query(Criteria.where("bucketInfo._id").is(bid)).skip(page * size).limit(size), BackupSnapshot.class);
        IPage<BackupSnapshot> backupSnapshotIPage = new Page<>();
        backupSnapshotIPage.setSize(snapshots.size());
        backupSnapshotIPage.setRecords(snapshots);
        backupSnapshotIPage.setCurrent(page);
        backupSnapshotIPage.setTotal(mongoTemplate.count(new Query(Criteria.where("bucketInfo._id").is(bid)),BackupSnapshot.class));
        return backupSnapshotIPage;
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean stop(String taskId) {
       Optional.ofNullable(redisTemplate.opsForValue().get(RedisConstant.BACKUP_TASK+":"+taskId)).ifPresent(r->{
           if (!r.equals(RedisConstant.BACKUP_TASK_READY))
               throw new RuntimeException("任务不存在或正在进行备份");
       });
       manage.pauseJob(String.valueOf(taskId));
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+taskId,RedisConstant.BACKUP_TASK_STOP);
        return true;
    }

    @SneakyThrows
    @Override
    @Transactional
    public boolean updateTask(BackupTask task) {
        manage.updateJob(QuartzJob.builder().jobId(task.getId()).jobName(task.getId()).bid(task.getBid()).triggerName(String.valueOf(task.getId())).startTime(task.getStartTime()).timeGap(task.getTimeGap()).build());
        return updateById(task);
    }

    @SneakyThrows
    @Override
    @Transactional
    public boolean deleteTask(String taskId) {
        Optional.ofNullable(redisTemplate.opsForValue().get(RedisConstant.BACKUP_TASK+":"+taskId)).ifPresent(r->{
            if (r.equals(RedisConstant.BACKUP_TASK_RUNNING))
                throw new RuntimeException("任务不存在或正在进行备份");
        });
        manage.deleteJob(taskId);
        redisTemplate.delete(RedisConstant.BACKUP_TASK+":"+taskId);
    return removeById(taskId);
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean resumeTask(String taskId) {
        Optional.ofNullable(redisTemplate.opsForValue().get(RedisConstant.BACKUP_TASK+":"+taskId)).ifPresent(r->{
            if (!r.equals(RedisConstant.BACKUP_TASK_STOP))
                throw new RuntimeException("无法恢复未暂停的任务");
        });
        manage.resumeJob(taskId);
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+taskId,RedisConstant.BACKUP_TASK_READY);
        return true;
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean executeTask(String taskId) {
        Optional.ofNullable(redisTemplate.opsForValue().get(RedisConstant.BACKUP_TASK+":"+taskId)).ifPresent(r->{
            if (r.equals(RedisConstant.RECOVER_TASK_RUNNING))
                throw new RuntimeException("任务正在运行");
            else if (r.equals(RedisConstant.BACKUP_TASK_STOP))
                throw new RuntimeException("该任务已被暂停使用");
        });
        manage.runAJobNow(taskId);
        return true;
    }

    @Override
    public String getStatus(String taskId) {
        return redisTemplate.opsForValue().get(RedisConstant.BACKUP_TASK+":"+taskId);
    }
}
