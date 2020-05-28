package com.song.utils;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 09:46
 * @description: 系统统一时钟获取
 */

public abstract class AbstractClock {

    public AbstractClock(){

    }

    public abstract Long getSystemTime();

    public static AbstractClock getSystemClock(){
        return new AbstractClock.SystemClock();
    }

    private static final class SystemClock extends AbstractClock{

        private SystemClock(){

        }

        @Override
        public Long getSystemTime() {
            return System.currentTimeMillis();
        }
    }
}
