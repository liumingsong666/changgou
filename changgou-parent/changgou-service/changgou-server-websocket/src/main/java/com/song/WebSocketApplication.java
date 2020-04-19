package com.song;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-16 13:42
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
public class WebSocketApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebSocketApplication.class,args);
    }
}
