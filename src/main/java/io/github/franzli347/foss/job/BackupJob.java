package io.github.franzli347.foss.job;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.model.entity.BackupSnapshot;
import io.github.franzli347.foss.model.entity.Bucket;
import io.github.franzli347.foss.model.entity.BucketPrivilege;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.protobuf.FileTransferClient;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.web.service.FilesService;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @ClassName BackupJob
 * @Author AlanC
 * @Date 2023/4/14 16:15
 **/
@Slf4j
@Component
public class BackupJob implements Job {
    @Autowired
    private  FilesService filesService;
    @Autowired
    private  MongoTemplate mongoTemplate;
    @Autowired
    private  BucketPrivilegeService privilegeService;
    @Autowired
    private  BucketService bucketService;
    @Autowired
    private  StringRedisTemplate redisTemplate;
    @Value("${gRPC.backupPath}")
    private String snapshotPath;
    @Value("${gRPC.Server.HOST}")
    private String host;
    @Value("${gRPC.Server.Port}")
    private int port;
    @Value("${pathMap.source}")
    String filePath;
    @SneakyThrows
    @Transactional
    public void run(String taskId,int bid){
        log.info("backup running,"+bid);
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+taskId,RedisConstant.BACKUP_TASK_RUNNING);
        List<Files> files=filesService.list(new LambdaQueryWrapper<Files>().eq(Files::getBid, bid));
        Bucket bucketInfo = bucketService.getById(bid);
        List<BucketPrivilege> bucketPrivilegesInfo=privilegeService.list(new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid,bid));
        long snapshotId = IdUtil.getSnowflakeNextId();
        mongoTemplate.save(BackupSnapshot.builder()
                .executedTime(new Date())
                .id(String.valueOf(snapshotId))
                .bucketInfo(bucketInfo)
                .taskId(taskId)
                .privilegeInfos(bucketPrivilegesInfo)
                .filesInfo(files).build());
        FileTransferClient fileTransferClient = new FileTransferClient(host,port);
        for(Files file:files){//gRPC传输至备份服务器
            fileTransferClient.upload(snapshotPath+snapshotId+"/"+file.getFileName(),filePath+file.getPath(),snapshotPath+snapshotId+"/");
        }
        fileTransferClient.shutdown();
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+taskId,RedisConstant.BACKUP_TASK_READY);
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        run((String)jobExecutionContext.getJobDetail().getJobDataMap().get("jobId"),(Integer) jobExecutionContext.getJobDetail().getJobDataMap().get("bid"));
    }
}
