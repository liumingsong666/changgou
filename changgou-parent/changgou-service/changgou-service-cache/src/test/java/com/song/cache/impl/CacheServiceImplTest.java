package com.song.cache.impl;


import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

public class CacheServiceImplTest {

    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->
                countDownLatch.countDown()).start();
        countDownLatch.await();
        System.out.println("线程已结束");
    }

    @Test
    public void current() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "xxxx";
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        System.out.println("主要线程");
        stringCompletableFuture.get();
        System.out.println("结束");

    }

    @Test
    public void Test1(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            executorService.execute(() -> System.out.println("回调函数"));
        });
        test4(cyclicBarrier);
        System.out.println("结束");
        while (true);
    }

    public void test4(CyclicBarrier cyclicBarrier){
        new Thread(()->{
            try {
                Thread.sleep(1000);
                System.out.println("线程1");
                cyclicBarrier.await();
                System.out.println("向下执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                System.out.println("线程2");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void lock(){
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        readLock.lock();
        try {
            System.out.println("xx");
        }finally {
            readLock.unlock();
        }

    }
}