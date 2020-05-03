package com.song.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>SpringApplication封装
 * </p>
 *
 */
@Component
public class SpringUtils implements ApplicationContextAware{
    private static ApplicationContext context;
    
    /**
     * 此方法谨慎使用
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
        context = paramApplicationContext;
    }
    
    public static <T> T  getBean(Class<T> c){
        return context.getBean(c);
    }
    
    public static Object  getBean(String beanName){
        return context.getBean(beanName);
    }

}
