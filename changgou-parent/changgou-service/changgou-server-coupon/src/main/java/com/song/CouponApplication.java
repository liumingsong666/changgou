package com.song;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: mingsong.liu
 * @date: 2020-05-24 14:01
 * @description:
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.song.mapper")
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class,args);
    }
}
