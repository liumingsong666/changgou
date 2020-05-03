package com.song.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-28 10:44
 * @Description:
 */
@Slf4j
@Component
@Aspect
@ConditionalOnClass(name = "org.springframework.cloud.openfeign.FeignClient")
public class FeignLogAspect {


    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void FeignLogPointCut(){}

    @Around("FeignLogPointCut()")
    public Object FeignLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String requestUri = null;
        Annotation[] declaredAnnotations = signature.getMethod().getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if(annotation instanceof RequestMapping){
                String[] value = ((RequestMapping) annotation).value();
                requestUri=value[0];
            }
        }
        Long start = 0L;
        Object proceed=null;
        try {
            start=System.currentTimeMillis();
            proceed = joinPoint.proceed();
            return proceed;
        }finally {
            long time = System.currentTimeMillis() - start;
            log.info("请求路径：{},请求参数：{},响应参数：{},响应时间：{}",requestUri, JSON.toJSONString(joinPoint.getArgs()),JSON.toJSONString(proceed),time);
        }

    }
}
