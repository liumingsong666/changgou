package com.song.scheduled;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: mingsong.liu
 * @date: 2020-05-05 15:07
 * @description: 定时任务分布式锁的配置
 */

@Configuration
//defaultLockAtMostFor 指定在执行节点结束时应保留锁的默认时间, 防止节点挂了出现死锁
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S")
public class ScheduledLockConfig {

    @Bean
    public LockProvider lockProvider(RedisTemplate redisTemplate){
        return  new RedisLockProvider(redisTemplate.getConnectionFactory());
    }

}
