package com.song.aspect;

import com.alibaba.fastjson.JSON;
import com.song.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Type;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-28 09:46
 * @Description:
 */

@Component
@Aspect
@Order(10)
@Slf4j
public class ExceptionAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void exceptionPointCut(){}

    @Around("exceptionPointCut()")
    public Object exceptionAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            proceed = joinPoint.proceed();
            return proceed;
        } catch (Exception e) {
            MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
            log.info("类：{},方法：{},参数：{},异常为：{}",joinPoint.getTarget().getClass().getName(),methodSignature.toShortString(), JSON.toJSONString(joinPoint.getArgs()),e.getMessage());
            Type type = methodSignature.getMethod().getGenericReturnType();
            if(type.getTypeName().contains("com.song.entity.Result")){
                return Result.fail(HttpStatus.BAD_REQUEST.value(),e.getMessage());
            }

            throw e;
        }
    }
}
