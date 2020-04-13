package com.changgou.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("接收结果")
@Data
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
}
