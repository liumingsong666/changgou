package com.song.entity;


/**
 * 常量的实体类
 */
public interface Constant {

    interface redis {
        String REDIS_IMAGE_CODE = "redis:image:code:";
        String REDIS_IP_LIMIT = "redis:ip:limit:";
    }

    interface token {
        String TOKEN_AUTHOR = "AUTHOR";
        String SESSION_USER = "username";
    }

    interface session{
        String REDIS_SESSION_PRENAME = "changgou:session";
        int REDIS_SESSION_INVALIDATE = 36000;
    }



}
