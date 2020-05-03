package com.song.exception;

import com.song.entity.Result;
import com.song.entity.StatusCode;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class,MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result BindException(Exception e){
        BindingResult bindingResult;
        if(e instanceof BindException){
            BindException bindException = (BindException) e;
            bindingResult=bindException.getBindingResult();
        }else {
            MethodArgumentNotValidException methodArgumentNotValidException= (MethodArgumentNotValidException) e;
            bindingResult=methodArgumentNotValidException.getBindingResult();
        }

        return getResult(bindingResult);

    }


    private Result getResult(BindingResult bindingResult2) {
        BindingResult bindingResult = bindingResult2;
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError fieldError : bindingResult.getAllErrors()) {
            stringBuilder.append("  "+fieldError.getDefaultMessage());
        }
        log.info(stringBuilder.toString());
        return Result.fail(HttpStatus.BAD_REQUEST.value(),stringBuilder.toString());
    }


}
