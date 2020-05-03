package com.song.filter;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import org.aspectj.weaver.tools.cache.CacheBacking;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.annotation.WebListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: mingsong.liu
 * @date: 2020-04-30 09:33
 * @description: 配置监听器，实现对路径的映射，缓存之后可以先对请求进行路径校验
 */

//@WebListener   @ServletComponentScan还需要在启动类上添加
public class UrlListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if(contextRefreshedEvent.getApplicationContext().getParent()==null){
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            //获取方法和路径的映射
            RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
            Set<String> set = Sets.newHashSet();
            for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
                //获取映射路径
                Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                set.addAll(patterns);
            }



        }
    }
}
