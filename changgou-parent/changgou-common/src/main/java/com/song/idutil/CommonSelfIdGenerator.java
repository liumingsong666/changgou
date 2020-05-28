package com.song.idutil;

import com.google.common.base.Preconditions;
import com.song.utils.AbstractClock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 10:09
 * @description: 获取id
 */

public class CommonSelfIdGenerator implements IdGenerator{

    public static final long CHANGGOU_EPOCH;
    private static final long SEQUENCE_BITS = 10L;
    private static final long WORKER_ID_BITS = 12L;
    private static final long SEQUENCE_MASK = 1023L;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = 10L;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
    private static final long WORKER_ID_MAX_VALUE = 4096L;
    private static AbstractClock clock = AbstractClock.getSystemClock();
    private static long workerId;
    private long sequence;
    private long lastTime;

    public CommonSelfIdGenerator() {
    }

    public static void setWorkerId(Long workerId) {
        Preconditions.checkArgument(workerId >= 0L && workerId < 4096L);
        CommonSelfIdGenerator.workerId = workerId;
    }

    public static long getWorkerIdLength() {
        return 12L;
    }

    @Override
    public synchronized Number generateId() {
        long time = clock.getSystemTime();
        Long t = System.currentTimeMillis();
        Preconditions.checkState(this.lastTime <= time, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", new Object[]{this.lastTime, time});
        if (this.lastTime == time) {
            if (0L == (++this.sequence & 1023L)) {
                time = this.waitUntilNextTime(time);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTime = time;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(CHANGGOU_EPOCH)));
        return time - CHANGGOU_EPOCH << 22 | workerId << 10 | this.sequence;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        setWorkerId(1L);
        Number id = (new CommonSelfIdGenerator()).generateId();
        System.err.println(id + " " + 9223372036854775807L);
    }

    private long waitUntilNextTime(long lastTime) {
        long time;
        for(time = clock.getSystemTime(); time <= lastTime; time = clock.getSystemTime()) {
        }

        return time;
    }

    public static void setClock(AbstractClock clock) {
        CommonSelfIdGenerator.clock = clock;
    }

    public static long getWorkerId() {
        return workerId;
    }

    static {
        //设置时钟的起始时间为2020-1-1 00:00:00:00   注意要比本地多一个月
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 0, 1);
        //设置时分秒 和毫秒初始化
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        CHANGGOU_EPOCH = calendar.getTimeInMillis();
    }
}
