package com.song;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:58
 * @Description:
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.song.mapper"})
public class LoginApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoginApplication.class,args);
    }
}
