package com.song;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:58
 * @Description:
 */

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class,DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class LoginApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoginApplication.class,args);
    }
}
