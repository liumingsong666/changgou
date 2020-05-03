package com.song.job;


import com.alibaba.fastjson.JSON;
import com.song.entity.Result;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-27 14:17
 * @Description:
 */

@PersistJobDataAfterExecution
//不允许并发执行某个定时任务
@DisallowConcurrentExecution
public class ScheduledJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        String username = jobExecutionContext.getJobDetail().getJobDataMap().getString("username");
        long jobRunTime = jobExecutionContext.getJobRunTime();
        String format = LocalDate.ofEpochDay(jobRunTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(username+Thread.currentThread().getName()+"--"+format);
    }

}
