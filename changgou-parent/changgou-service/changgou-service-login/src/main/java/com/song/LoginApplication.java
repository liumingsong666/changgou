package com.song;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:58
 * @Description:
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.song.dao"})
public class LoginApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoginApplication.class,args);
    }
}
