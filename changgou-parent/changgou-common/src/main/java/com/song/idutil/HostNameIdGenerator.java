package com.song.idutil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 11:00
 * @description: 通过主机生成id
 */

public class HostNameIdGenerator {

    private final CommonSelfIdGenerator commonSelfIdGenerator = new CommonSelfIdGenerator();

    public HostNameIdGenerator() {
    }

    static void initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException var5) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }

        String hostName = address.getHostName();

        Long workerId;
        try {
            workerId = Long.valueOf(hostName.replace(hostName.replaceAll("\\d+$", ""), ""));
        } catch (NumberFormatException var4) {
            throw new IllegalArgumentException(String.format("Wrong hostname:%s, hostname must be end with number!", hostName));
        }

        CommonSelfIdGenerator.setWorkerId(workerId);
    }

    public Number generateId() {
        return this.commonSelfIdGenerator.generateId();
    }

    static {
        initWorkerId();
    }
}
