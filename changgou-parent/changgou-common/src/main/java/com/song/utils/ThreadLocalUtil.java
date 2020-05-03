package com.song.utils;


/**
 * @Author: mingsong.liu
 * @Date: 2020-04-23 14:16
 * @Description:
 */

public class ThreadLocalUtil {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static Object getLocal(){
        return threadLocal.get();
    }

    public static void setLocal(Object object){
        threadLocal.set(object);
    }

    public static void deleteLocal(){
        threadLocal.remove();
    }
}
