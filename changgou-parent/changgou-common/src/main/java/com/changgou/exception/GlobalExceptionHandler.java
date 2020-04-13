package com.changgou.exception;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        System.out.println(e.getStackTrace() );
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
