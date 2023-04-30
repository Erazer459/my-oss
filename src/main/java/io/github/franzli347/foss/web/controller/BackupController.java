package io.github.franzli347.foss.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.constant.AuthConstant;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.model.entity.BackupSnapshot;
import io.github.franzli347.foss.model.entity.BackupTask;
import io.github.franzli347.foss.web.service.BackupTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName BackupController
 * @Author AlanC
 * @Date 2023/4/13 15:47
 **/
@RestController
@RequestMapping("/backup")
@Tag(name = "bucket备份模块")
public class BackupController {
    private final BackupTaskService backupTaskService;
    public final StringRedisTemplate redisTemplate;
    public BackupController(BackupTaskService backupTaskService, StringRedisTemplate redisTemplate) {
        this.backupTaskService = backupTaskService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/init")
    @Operation(summary = "初始化备份任务,返回备份任务ID")
    @CheckBucketPrivilege(spelString = "#task.bid",argType = AuthConstant.BID,privilege = AuthConstant.OWNER)
    public String init(@RequestBody BackupTask task){
        task.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        String init = backupTaskService.init(task);
        backupTaskService.save(task);
        redisTemplate.opsForValue().set(RedisConstant.BACKUP_TASK+":"+task.getId(),RedisConstant.BACKUP_TASK_READY);
      return init;
    }
    @PostMapping("/recover/{snapshotId}")
    @Operation(summary = "从备份中恢复")
    @Parameter(name = "snapshotId",description = "备份记录ID")
    public String recover(@PathVariable String snapshotId){
         backupTaskService.recover(snapshotId, StpUtil.getLoginIdAsInt());
        return "正在从备份中恢复";
    }
    @GetMapping("/getTask/{bid}")
    @Operation(summary = "获取bucket备份任务")
    @Parameter(name = "bid",description = "bucket id")
    public BackupTask getTask(@PathVariable int bid){
        return backupTaskService.getOne(new LambdaQueryWrapper<BackupTask>().eq(BackupTask::getBid,bid));
    }
    @GetMapping("/getSnapshots/{bid}/{page}/{size}")
    @Operation(summary = "获取备份快照列表")
    @Parameter(name = "bid",description = "bucket id")
    @Parameter(name = "page",description = "页码")
    @Parameter(name = "size",description = "每页数量")
    @CheckBucketPrivilege(spelString = "#bid",argType = AuthConstant.BID,privilege = AuthConstant.OWNER)
    public IPage<BackupSnapshot> getSnapshots(@PathVariable int bid, @PathVariable int page, @PathVariable int size){
        return backupTaskService.getSnapshots(bid,page,size);
    }
    @PostMapping("/stop/{taskId}")
    @Operation(summary = "暂停任务")
    @Parameter(name = "taskId",description = "任务id")
    public boolean stopTask(@PathVariable String taskId){
        return backupTaskService.stop(taskId);
    }
    @PostMapping("/update")
    @Operation(summary = "修改任务")
    public boolean updateTask(@RequestBody BackupTask task){
        return backupTaskService.updateTask(task);
    }
    @DeleteMapping("/delete/{taskId}")
    @Operation(summary = "删除任务")
    @Parameter(name = "taskId",description = "任务id")
    public boolean deleteTask(@PathVariable String taskId){
        return backupTaskService.deleteTask(taskId);
    }
    @PostMapping("/resume/{taskId}")
    @Operation(summary = "恢复暂停任务")
    @Parameter(name = "taskId",description = "任务id")
    public boolean resumeTask(@PathVariable String taskId){
        return backupTaskService.resumeTask(taskId);
    }
    @PostMapping("/execute/{taskId}")
    @Operation(summary = "立即执行任务")
    @Parameter(name = "taskId",description = "任务id")
    public boolean executeTask(@PathVariable String taskId){
        return backupTaskService.executeTask(taskId);
    }
    @GetMapping("/getStatus/{taskId}")
    @Operation(summary = "获取任务状态(就绪，停止，进行中)")
    public String getStatus(@PathVariable String taskId){
        return backupTaskService.getStatus(taskId);
    }
}
