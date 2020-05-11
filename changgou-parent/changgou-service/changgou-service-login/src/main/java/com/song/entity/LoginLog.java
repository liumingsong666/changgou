package com.song.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mingsong.liu
 * @description 用户登陆日志表(login_log)表实体类
 * @date 2020-05-10 14:24:21
 */

@Table(name = "login_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户登陆日志表")
public class LoginLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    @ApiModelProperty("登陆日志ID")
    private Long loginId;

    @Column(name = "customer_id")
    @ApiModelProperty("登陆用户ID")
    private Long customerId;

    @Column(name = "login_time")
    @ApiModelProperty("用户登陆时间")
    private Date loginTime;

    @Column(name = "login_ip")
    @ApiModelProperty("登陆IP")
    private String loginIp;

    @Column(name = "login_type")
    @ApiModelProperty("登陆类型：1手机号，2用户名，3微信")
    private Integer loginType;

    @Column(name = "login_flag")
    @ApiModelProperty("登陆状态：0失败 1成功")
    private Integer loginFlag;

}