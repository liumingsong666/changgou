package com.song.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-22 16:49
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo  implements Serializable {


    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("姓名")
    protected String name;

    @ApiModelProperty("password")
    protected String password;

    @ApiModelProperty("手机号")
    protected Integer phone;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("登录时间")
    private Date loginTime;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("版本号")
    private Integer version;
}
