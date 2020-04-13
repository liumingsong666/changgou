package com.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:58
 * @Description:
 */

@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }
}
