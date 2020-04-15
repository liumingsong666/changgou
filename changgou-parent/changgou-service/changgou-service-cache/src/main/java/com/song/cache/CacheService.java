package com.song.cache;

public interface CacheService {

    Object setCacheInfo(String key,Object value);

    Object getCacheInfo(String key);

    void deleteCacheInfo(String key);
}
