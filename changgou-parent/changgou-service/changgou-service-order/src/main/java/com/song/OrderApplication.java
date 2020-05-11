package com.song;

import com.song.interceptor.TokenInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 11:23
 * @description:
 */

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan(basePackages = "com.song.dao")
public class OrderApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).order(-1).addPathPatterns("/**");
    }
}
