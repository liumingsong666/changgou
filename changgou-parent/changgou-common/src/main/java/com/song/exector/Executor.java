package com.song.exector;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 15:43
 * @description: 统一的线程池使用
 */

public class Executor {

    private static final Logger log = LoggerFactory.getLogger(Executor.class);
    private static int availableCores = getAvailableCores();
    private static ThreadPoolExecutor computeExecutorService;
    private static ThreadPoolExecutor ioExecutorService;

    public Executor() {
    }

    public static void submitComputeTask(Runnable task) {
        if (null == computeExecutorService) {
            computeExecutorService = new ThreadPoolExecutor(4, 32, 5000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1024), (new ThreadFactoryBuilder()).setNameFormat("commons-compute-pool-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        }

        computeExecutorService.submit(task);
    }

    public static void submitIOTask(Runnable task) {
        if (null == ioExecutorService) {
            ioExecutorService = new ThreadPoolExecutor(availableCores, availableCores, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1024), (new ThreadFactoryBuilder()).setNameFormat("commons-io-pool-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        }

        ioExecutorService.submit(task);
    }

    public static void shutdown() {
        if (null != computeExecutorService && !computeExecutorService.isShutdown()) {
            computeExecutorService.shutdownNow();
        }

        if (null != ioExecutorService && !ioExecutorService.isShutdown()) {
            ioExecutorService.shutdownNow();
        }

    }

    private static int getAvailableCores() {
        int availableCores = (int)Math.floor((double)(Runtime.getRuntime().availableProcessors() / 2)) - 1;
        if (availableCores < 1) {
            availableCores = 1;
        }

        log.info("find availableCores：{}", availableCores);
        return availableCores;
    }
}
