package com.song.scheduled;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: mingsong.liu
 * @date: 2020-05-05 15:12
 * @description: 定时任务锁的测试
 */

@Component
public class TestScheduledLock {

    @Scheduled(cron = "0 0/2 * * * ?")
    //lockAtLeastForString 避免不通的节点的服务启动的时间差
    //lockAtMostForString  线程执行完之后的锁的保留时间
    @SchedulerLock(name = "test",lockAtLeastForString = "PT5S",lockAtMostForString = "PT1M")
    public void test(){
        System.out.println(Thread.currentThread().getName());
    }
}
