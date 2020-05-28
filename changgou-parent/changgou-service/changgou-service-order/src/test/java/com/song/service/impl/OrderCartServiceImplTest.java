package com.song.service.impl;

import com.song.entity.IdWorker;
import com.song.entity.ProductInfo;
import com.song.exector.Executor;
import com.song.idutil.IPIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@RunWith(SpringRunner.class)
@SpringBootTest
public class  OrderCartServiceImplTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(1);
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        System.out.println(redisTemplate.opsForValue().setIfAbsent("key", "json", 3, TimeUnit.MINUTES));
        //redisTemplate.opsForValue().set("bbb","json",1,TimeUnit.MINUTES);
        //System.out.println(redisTemplate.opsForValue().setIfPresent("bbb","json",3,TimeUnit.MINUTES));
        Long key = redisTemplate.getExpire("key");
        System.out.println(key);
    }

    @Test
    public void uuid() throws InterruptedException {
        //177
        //318
        //87

        IdWorker idWorker = new IdWorker();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //HashSet<Long> set = new HashSet<>(100000);
        Set<Long> set = new CopyOnWriteArraySet<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 10000; i1++) {
                        set.add(idWorker.nextId());
                    }
                    countDownLatch.countDown();
                }
            });


        }
        countDownLatch.await();

        System.out.println("生成id时间：" + (System.currentTimeMillis() - l));
        System.out.println(set.size());
    }

}