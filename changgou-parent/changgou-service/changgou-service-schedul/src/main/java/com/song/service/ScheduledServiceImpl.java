package com.song.service;

import com.song.entity.Result;
import com.song.dto.ScheduledDto;
import com.song.exception.ServiceException;
import com.song.job.ScheduledJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-27 17:58
 * @Description:
 */
@Service
@Slf4j
public class ScheduledServiceImpl implements ScheduledService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public Result startScheduled(ScheduledDto scheduledDto) {
        //检验cron格式
        if (!CronExpression.isValidExpression(scheduledDto.getCron())) {
            return Result.fail(HttpStatus.BAD_REQUEST.value(), "cron格式不正确");
        }
        //创建任务工作
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).usingJobData("username", scheduledDto.getUsername())
                .withIdentity(scheduledDto.getName(), scheduledDto.getGroup()).storeDurably().build();
        //创建cron对象
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduledDto.getCron());
        //创建trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder).withIdentity(scheduledDto.getName(), scheduledDto.getGroup())
                .forJob(jobDetail).startAt(scheduledDto.getStartDate()).build();

        try {

            if(scheduler.checkExists(jobDetail.getKey())){
                return  Result.success("任务已经在运行中");
            }
            scheduler.scheduleJob(jobDetail, trigger);
            return Result.success("任务开启成功", null);
        } catch (SchedulerException e) {
            log.info("任务开启失败：", e);
            throw new ServiceException("任务开启失败");
        }

    }

    @SneakyThrows
    @Override
    public Result pauseScheduled(ScheduledDto scheduledDto) {
        JobKey jobKey = JobKey.jobKey(scheduledDto.getName(), scheduledDto.getGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return Result.fail(HttpStatus.BAD_REQUEST.value(), "该定时任务不存在");
        }
        scheduler.pauseJob(jobKey);
        return Result.success("暂停成功");
    }

    @SneakyThrows
    @Override
    public Result deleteScheduled(ScheduledDto scheduledDto) {
        JobKey jobKey = JobKey.jobKey(scheduledDto.getName(), scheduledDto.getGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (Objects.isNull(jobDetail) || !scheduler.checkExists(jobKey)) {
            return Result.fail(HttpStatus.BAD_REQUEST.value(), "job任务不存在");
        }
        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
        return Result.success("删除任务成功");

    }

    @SneakyThrows
    @Override
    public Result resumeScheduled(ScheduledDto scheduledDto) {
        JobKey jobKey = JobKey.jobKey(scheduledDto.getName(), scheduledDto.getGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return Result.fail(HttpStatus.BAD_REQUEST.value(), "没有该任务");
        }
        scheduler.resumeJob(jobKey);
        return Result.success("任务重新开始");
    }

    @SneakyThrows
    @Override
    public Result resetScheduled(ScheduledDto scheduledDto) {
        if (!CronExpression.isValidExpression(scheduledDto.getCron())) {
            return Result.fail(HttpStatus.BAD_REQUEST.value(), "cron格式不正确");
        }
        JobKey jobKey = JobKey.jobKey(scheduledDto.getName(), scheduledDto.getGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduledDto.getName(), scheduledDto.getGroup());

        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduledDto.getCron()).withMisfireHandlingInstructionDoNothing();
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            trigger.getJobDataMap().put("username", scheduledDto.getUsername());
            scheduler.rescheduleJob(triggerKey, trigger);
            return Result.success("修改成功");
        }

        return Result.fail(HttpStatus.BAD_REQUEST.value(), "job任务不存在");
    }
}
