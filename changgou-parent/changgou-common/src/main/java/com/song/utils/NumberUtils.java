/**
 * flyiu.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package com.song.utils;

import java.math.BigDecimal;

/** 
 * <p>
 * </p>
 *
 * @author flyiu@flyiu.com
 * @version $Id: NumberUtils.java, v 0.1 2018年7月9日 下午10:27:17 flyiu Exp $
 */
public class NumberUtils {

    /**
     * 向上取整，2位小数
     * 四舍五入
     * @param number
     * @return
     */
    public static double scale(double number) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
