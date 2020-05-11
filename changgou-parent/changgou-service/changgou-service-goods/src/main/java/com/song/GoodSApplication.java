package com.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.song.dao"})
@EnableSwagger2
public class GoodSApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodSApplication.class,args);
    }
}
