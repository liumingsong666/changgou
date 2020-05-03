/**
 * flyiu.com Inc.
 * Copyright (c) 2018-2020 All Rights Reserved.
 */
package com.song.utils;

import com.song.aspect.RequestContextAspect;
import com.song.entity.RequestDTO;

/**
 * 获取上下文
 */
public class ContextUtils {

    /**
     * 获取requestDTO
     * 
     * @return
     */
    public static RequestDTO<?> getRequestDTO() {
        return RequestContextAspect.local.get();
    }
}
