package io.github.franzli347.foss.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.middleware.manager.QuartzManage;
import io.github.franzli347.foss.model.entity.*;
import io.github.franzli347.foss.model.job.QuartzJob;
import io.github.franzli347.foss.protobuf.FileTransferClient;
import io.github.franzli347.foss.web.mapper.BackupTaskMapper;
import io.github.franzli347.foss.web.service.BackupTaskService;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.web.service.FilesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Objects;
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
    public long init(BackupTask task) {
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        manage.addJob(QuartzJob.builder()
                .jobId(snowflakeNextId)
                .jobName(task.getTaskName())
                .triggerName(String.valueOf(task.getId()))
                .methodName("run")
                .timeGap(task.getTimeGap())
                .startTime(task.getStartTime())
                        .timeGap(task.getTimeGap())
                .bid(task.getBid()).build());
        return snowflakeNextId;
    }

    @Override
    @Async("asyncExecutor")
    @Transactional
    public void recover(long snapshotId,int uid) {
    Optional.ofNullable(redisTemplate.opsForValue().get(String.valueOf(snapshotId))).ifPresent(r->{
    throw new RuntimeException("备份任务已在运行");
    });
        redisTemplate.opsForValue().set(RedisConstant.RECOVER_TASK+":"+snapshotId,RedisConstant.RECOVER_TASK_RUNNING);
        redisTemplate.expire(RedisConstant.RECOVER_TASK+":"+snapshotId,RedisConstant.FILE_TASK_EXPIRE, TimeUnit.SECONDS);
        BackupSnapshot snapshot=Optional.ofNullable(mongoTemplate.findOne(new Query(Criteria.where("_id").is(snapshotId)),BackupSnapshot.class)).orElseThrow(()->new RuntimeException("不存在此备份快照信息"));
        Integer bid = snapshot.getBucketInfo().getId();
        bucketService.update(snapshot.getBucketInfo(),new LambdaUpdateWrapper<Bucket>().eq(Bucket::getId,bid));
        for(Files file:filesService.list(new LambdaQueryWrapper<Files>().eq(Files::getBid,bid))){//清除旧纪录
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
        redisTemplate.opsForValue().getOperations().delete(RedisConstant.RECOVER_TASK+":"+snapshotId);
    }
}
