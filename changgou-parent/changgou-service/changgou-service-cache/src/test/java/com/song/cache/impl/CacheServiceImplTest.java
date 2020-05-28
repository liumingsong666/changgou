package com.song.cache.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceImplTest {


    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().setIfAbsent("key","json",1, TimeUnit.MINUTES);
    }

}