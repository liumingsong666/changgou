package com.song.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@ApiModel("接收结果")
@Data
@Builder
public class Result<T> {

    @ApiModelProperty("是否成功")
    private Boolean flag;
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("信息")
    private String message;
    @ApiModelProperty("数据")
    private T data;

    public Result(Boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    public Result(Boolean flag, Integer code, String message) {

        this.flag = flag;
        this.code = code;
        this.message = message;
    }


    public static<T> Result success(T data){

        return new Result(true, HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),data);
    }

    public static<T> Result success(String message, T data){

        return new Result(true, HttpStatus.OK.value(),message,data);
    }

    public static Result fail(int code,String message){

         return new Result(false,code,message);
    }
}
