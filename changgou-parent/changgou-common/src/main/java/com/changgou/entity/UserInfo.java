package com.changgou.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 16:32
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {


    @ApiModelProperty("姓名")
    protected String name;

    @ApiModelProperty("password")
    protected String password;

    @ApiModelProperty("手机号")
    protected Integer phone;
}
