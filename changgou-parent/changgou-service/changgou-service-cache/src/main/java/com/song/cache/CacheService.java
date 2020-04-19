package com.song.cache;

import java.util.concurrent.TimeUnit;

public interface CacheService {

    Object setCacheInfo(String key, Object value);

    Object setCacheInfo(String key, Object value, Long ttl, TimeUnit timeUnit);

    Object getCacheInfo(String key);

    boolean deleteCacheInfo(String key);
}
