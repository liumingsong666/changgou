package com.song.cache.impl;

import com.song.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 17:26
 * @Description: redis缓存
 */

@Service("redisCacheServiceImpl")
public class RedisCacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Object setCacheInfo(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public Object setCacheInfo(String key, Object value,Long ttl,TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,ttl, timeUnit);
        return true;
    }

    @Override
    public Object getCacheInfo(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean deleteCacheInfo(String key) {
        return redisTemplate.delete(key);
    }
}
