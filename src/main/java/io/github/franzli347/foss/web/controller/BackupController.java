package io.github.franzli347.foss.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.constant.AuthConstant;
import io.github.franzli347.foss.model.entity.BackupTask;
import io.github.franzli347.foss.web.service.BackupTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    public BackupController(BackupTaskService backupTaskService) {
        this.backupTaskService = backupTaskService;
    }

    @PostMapping("/init")
    @Operation(summary = "初始化备份任务,返回备份任务ID")
    @CheckBucketPrivilege(spelString = "#task.bid",argType = AuthConstant.BID,privilege = AuthConstant.OWNER)
    public long init(@RequestBody BackupTask task){
        task.setId(IdUtil.getSnowflakeNextId());
        long init = backupTaskService.init(task);
        backupTaskService.save(task);
      return init;
    }
    @PostMapping("/recover")
    @Operation(summary = "从备份中恢复")
    @Parameter(name = "snapshotId",description = "备份记录ID")
    public String recover(long snapshotId){
         backupTaskService.recover(snapshotId, StpUtil.getLoginIdAsInt());
        return "正在从备份中恢复";
    }
}
