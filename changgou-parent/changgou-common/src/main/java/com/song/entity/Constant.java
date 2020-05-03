package com.song.entity;


/**
 * 常量的实体类
 */
public interface Constant {

    interface redis {
        String REDIS_IMAGE_CODE = "REDIS_IMAGE_CODE_";
        String REDIS_IP_LIMIT = "REDIS_IP_LIMIT_";
        Long REDIS_IMAGE_TTL = 60L;
    }
    interface token {
        String TOKEN_AUTHOR="AUTHOR";
    }


}
