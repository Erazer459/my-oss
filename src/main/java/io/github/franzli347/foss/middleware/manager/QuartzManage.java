package io.github.franzli347.foss.middleware.manager;

import io.github.franzli347.foss.job.BackupJob;
import io.github.franzli347.foss.job.QuartzJob;
import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class QuartzManage {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @SneakyThrows
    public void addJob(QuartzJob job)  {
        //通过实体类和任务名创建 JobDetail
        JobDetail jobDetail = newJob(BackupJob.class)
                .withIdentity(job.getJobId())
                .usingJobData("jobId",job.getJobId())
                .usingJobData("bid",job.getBid()).build();
        //创建 Trigger
        SimpleTrigger trigger =(SimpleTrigger) newTrigger()
                .withIdentity(String.valueOf(job.getJobId()))
                .startAt(job.getStartTime())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(job.getTimeGap()*24))
                .build();
        //执行定时任务
        scheduler.scheduleJob(jobDetail,trigger);
    }

    /**
     * 更新job起始时间或时间间隔
     * @param quartzJob
     * @throws SchedulerException
     */
    public void updateJob(QuartzJob quartzJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobId());
        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(quartzJob.getTimeGap()*24);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).startAt(quartzJob.getStartTime()).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
    /**
     * 删除一个job
     * @param key
     * @throws SchedulerException
     */
    public void deleteJob(String key) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(key);
        scheduler.deleteJob(jobKey);
    }
    /**
     * 恢复一个job
     * @param key
     * @throws SchedulerException
     */
    public void resumeJob(String key) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(key);
        scheduler.resumeJob(jobKey);
    }
    /**
     * 立即执行job
     * @param key
     * @throws SchedulerException
     */
    public void runAJobNow(String key) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(key);
        scheduler.triggerJob(jobKey);
    }
    /**
     * 暂停一个job
     * @param key
     * @throws SchedulerException
     */
    public void pauseJob(String key) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(key);
        scheduler.pauseJob(jobKey);
    }
}

