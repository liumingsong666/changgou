package com.song.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderCartServiceImplTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(1);
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.opsForHash().put("CHANGGOU:ORDER:CART:" + "173111",1,2);
        Object o = redisTemplate.opsForHash().get("CHANGGOU:ORDER:CART:" + "173111", 1);
        System.out.println(o);
    }

}