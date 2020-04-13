package com.song.cache;

public interface CacheService {

    void setCacheInfo(String key,Object value);

    Object getCacheInfo(String key);
}
