package com.song;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.song.entity.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.annotation.WebServlet;


/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/12 20:53
 * @Description:
 */

@EnableEurekaClient
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
@EnableZuulProxy
@ServletComponentScan
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = Constant.session.REDIS_SESSION_INVALIDATE,redisNamespace = Constant.session.REDIS_SESSION_PRENAME)
@EnableOAuth2Sso
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }
}
