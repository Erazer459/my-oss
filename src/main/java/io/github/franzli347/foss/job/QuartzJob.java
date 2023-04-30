package io.github.franzli347.foss.job;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class QuartzJob {

    private String jobId;
    /**
     * 任务调用的方法名
     */
    private String methodName;
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 任务名
     */
    private String jobName;
    private Date startTime;
    private int timeGap;
    /**
     * bucketId
     **/
    private int bid;
}