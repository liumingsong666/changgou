package com.song.idutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 10:08
 * @description: 获取IP，设置机器码
 */

public class IPIdGenerator implements IdGenerator {

    private static final Logger log = LoggerFactory.getLogger(IPIdGenerator.class);
    private final CommonSelfIdGenerator commonSelfIdGenerator = new CommonSelfIdGenerator();

    public IPIdGenerator() {
    }

    static void initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException var5) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }

        byte[] ipAddressByteArray = address.getAddress();
        long workerId = (long)(((ipAddressByteArray[ipAddressByteArray.length - 2] & 3) << 8) + (ipAddressByteArray[ipAddressByteArray.length - 1] & 255));
        log.info("ip workerId:{}", workerId);
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        log.info("processName:{}", processName);
        CommonSelfIdGenerator.setWorkerId(workerId);
    }

    @Override
    public Number generateId() {
        return this.commonSelfIdGenerator.generateId();
    }

    static {
        initWorkerId();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        IPIdGenerator ipIdGenerator = new IPIdGenerator();
        for (int i = 0; i < 4; i++) {

            System.err.println((ipIdGenerator.generateId())+"---"+CommonSelfIdGenerator.getWorkerId());
        }

    }
}
