package com.song.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @Author: mingsong.liu
 * @Date: 2020-04-28 11:04
 * @Description:
 */

@Slf4j
//@Order(12)
//@Component
//@Aspect
public class ExceptionLogAspect {

    @Pointcut("execution(public * com.song..*(..))")
    public void ExceptionLogPointCut(){}

    @Around("ExceptionLogPointCut()")
    public Object ExceptionLogAround(ProceedingJoinPoint joinPoint){
        Object proceed=null;
        try {
            proceed = joinPoint.proceed();
            return proceed;
        }catch (Throwable throwable){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            log.info("类：{},方法：{},参数：{},异常为：{}",joinPoint.getTarget().getClass().getName(),methodSignature.toShortString(), JSON.toJSONString(joinPoint.getArgs()),throwable.getMessage());
            return proceed;
        }
    }
}
