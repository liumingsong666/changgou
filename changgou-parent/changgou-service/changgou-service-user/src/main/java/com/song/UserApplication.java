package com.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/13 00:24
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.song.mapper")
@EnableCaching
@EnableSwagger2
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600,redisNamespace = "changgou:user")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
