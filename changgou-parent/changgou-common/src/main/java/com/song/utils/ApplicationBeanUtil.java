package com.song.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: mingsong.liu
 * @date: 2020-05-26 22:41
 * @description: 获取bean
 */

@Component
@Order(2)
public class ApplicationBeanUtil implements ApplicationContextAware {


    private static  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationBeanUtil.applicationContext=applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return ApplicationBeanUtil.applicationContext.getBean(clazz);
    }


    public static <T> T getBean(String name,Class<T> clazz) {
        return ApplicationBeanUtil.applicationContext.getBean(name,clazz);
    }

    public static Object getBean(String name){
        return  applicationContext.getBean(name);
    }

 }
