package com.song.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-22 16:49
 * @Description: 用户信息表
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserFeignDto {


    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    protected String phone;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("更新用户时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("版本号")
    private Integer version;


    public UserInfo BeanCovert(String username,String password,Collection<? extends GrantedAuthority> authorities){
        UserInfo userInfo = new UserInfo(username, password, authorities);
        userInfo.setId(this.id);
        userInfo.setAge(this.age);
        userInfo.setCreatTime(this.creatTime);
        userInfo.setNickName(this.nickName);
        userInfo.setPhone(this.phone);
        userInfo.setVersion(this.version);
        return userInfo;
    }
}
