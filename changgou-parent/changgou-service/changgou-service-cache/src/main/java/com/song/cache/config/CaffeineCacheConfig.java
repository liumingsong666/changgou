package com.song.cache.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.assertj.core.util.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 21:33
 * @Description:
 */

@Configuration
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<CaffeineCache> cacheTypes = Lists.newArrayList();
        for (CacheType value : CacheType.values()) {
            CaffeineCache caffeineCache = new CaffeineCache(value.name(), Caffeine.newBuilder().initialCapacity(50)
                    .maximumSize(100).expireAfterWrite(value.expire, value.timeUnit).build((CacheLoader) o -> null));
            cacheTypes.add(caffeineCache);
        }
        simpleCacheManager.setCaches(cacheTypes);
        return simpleCacheManager;
    }
    //通过枚举类可以实现多个缓存，定制化实现存储
    enum CacheType {
        IP(15,TimeUnit.MINUTES),
        IPCount(3,TimeUnit.SECONDS);

        private int expire;
        private TimeUnit timeUnit;

        CacheType(int expire,TimeUnit timeUnit) {
            this.expire = expire;
            this.timeUnit=timeUnit;
        }

    }
}
