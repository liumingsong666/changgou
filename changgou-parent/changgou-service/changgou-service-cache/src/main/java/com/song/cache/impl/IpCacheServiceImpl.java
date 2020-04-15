package com.song.cache.impl;


import com.song.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/1 22:38
 * @Description: 缓存实现类
 */

@Slf4j
@Service
public class IpCacheServiceImpl implements CacheService {


    @Override
    @CachePut(value = "IP",key = "#key",unless = "#result==null")
    public Object setCacheInfo(String key, Object value) {
        log.info("该ip已经被限制：{}",key);
        return value;
    }

    @Override
    @Cacheable(value = "Ip",key = "#key",unless = "#result==null")
    public Object getCacheInfo(String key) {
        log.info("获取key");
        return null;
    }

    @Override
    @CacheEvict(value = "IP",key = "#key")
    public void deleteCacheInfo(String key) {
        log.info("删除key");
    }

}
