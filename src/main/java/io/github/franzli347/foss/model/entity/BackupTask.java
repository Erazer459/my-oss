package io.github.franzli347.foss.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName BackupTask
 * @Author AlanC
 * @Date 2023/4/13 16:45
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_backup_task")
public class BackupTask{
    @Schema(description = "任务id")
    @Hidden
    private long id;
    @Schema(description = "任务名称")
    private String taskName;
    @Schema(description = "bucketId")
    private int bid;
    @Schema(description = "起始时间(精确到秒)")
    private Date startTime;
    @Schema(description = "时间间隔(单位为天)")
    private int timeGap;
}
