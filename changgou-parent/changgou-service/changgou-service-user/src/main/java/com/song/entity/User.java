package com.song.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @description 用户表(tb_user)表实体类
 * @date 2020-04-13 00:28:07
 */
@Table(name = "tb_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户表")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("昵称")
    @Column(name = "nick_name")
    private String nickName;

    @ApiModelProperty("password")
    @Column(name = "password")
    private String password;

    @ApiModelProperty("手机号")
    @Column(name = "phone")
    private String phone;

    @Column(name = "age")
    @ApiModelProperty("年龄")
    private Integer age;

    @Column(name = "creat_time")
    @ApiModelProperty("更新用户时间")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @Column(name = "role_id")
    @ApiModelProperty("角色id")
    private Integer roleId;

    @Column(name = "version")
    @ApiModelProperty("版本号")
    private Integer version;

}