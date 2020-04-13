package com.song.cache.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.song.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/1 22:38
 * @Description: 缓存实现类
 */

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator
            (new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));

    LoadingCache loadingCache = CacheBuilder.newBuilder().refreshAfterWrite(20, TimeUnit.SECONDS)
            .maximumSize(300).initialCapacity(50)
            .build(new CacheLoader<String, Object>() {
                //若缓存中没有，通过redis去取
                @Override
                public Object load(String key) throws Exception {
                    return getRedisValue(key);
                }

                @Override
                public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                    return listeningExecutorService.submit(()->getRedisValue(key));
                }

                private Object getRedisValue(String key){
                    return redisTemplate.opsForValue().get(key);
                }
            });

    @PostConstruct
    private void init(){
        //初始化数据
        loadingCache.put("key1",10);
        loadingCache.put("key2","你好");
    }
    @PreDestroy
    private void destroy(){
        try {
            listeningExecutorService.shutdown();
        }catch (Exception e){
            log.error("threadPool shutdown fail, message: {}",e.getMessage());
        }
    }

    @Override
    public void setCacheInfo(String key, Object value) {

    }

    @Override
    public Object getCacheInfo(String key) {
        return null;
    }
}
