package com.song.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-24 14:04
 * @Description: 解决全局跨域问题,该方式会在过滤器使用时失效，因为过滤器先执行
 */
//@Component
    @Slf4j
public class CrosInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域的header设置
        String credentials = response.getHeader("Access-Control-Allow-Credentials");
        log.info("credentials is {}",credentials);
        if(credentials!=null ) {
            return true;
        }
        //处理跨域名问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //防止乱码
        response.setHeader("Content-Type","application/json;charset=UTF-8");

        return true;


    }
}
