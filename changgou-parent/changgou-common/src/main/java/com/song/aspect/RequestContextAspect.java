package com.song.aspect;

import com.song.entity.RequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-28 10:24
 * @Description: 对请求上下文的拦截，存放请求
 */

@Aspect
@Slf4j
@Component
//@ConditionalOnProperty(value = "request.context.enable",havingValue = "true",matchIfMissing = true)
public class RequestContextAspect {

    public static ThreadLocal<RequestDTO<?>> local = new ThreadLocal();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.stereotype.Controller)")
    public void requestContestPointCut(){}

    @Around("requestContestPointCut()")
    public Object RequestContextAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg->{
            if(arg instanceof RequestDTO){
                local.set((RequestDTO) arg);
            }
        });

        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        }finally {
            local.remove();
        }
    }
}
