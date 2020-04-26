package com.song;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-16 13:42
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
public class WebSocketApplication implements ServletContextInitializer{

    public static void main(String[] args) {

        SpringApplication.run(WebSocketApplication.class,args);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void onStartup(ServletContext servletContext){
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","1024000");
    }
}
