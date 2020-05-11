package com.song.sub;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author: mingsong.liu
 * @date: 2020-05-03 18:18
 * @description:
 */

public class TestSub {

    @Test
    public void test(){

        String s = "aaaa";
        System.out.println(StringUtils.substringAfter(s,"b"));
        //System.out.println(StringUtils.substringAfterLast(s,"b"));
        //System.out.println(StringUtils.substringBetween(s,"b"));
    }
}
