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
 * @description 用户领卷表(coupon_user)表实体类
 * @date 2020-05-24 20:39:37
 */
@Table(name = "coupon_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户领卷表")
public class CouponUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty("id")
    private Integer id;

    @Column(name = "coupon_id")
    @ApiModelProperty("优惠券id")
    private String couponId;

    @Column(name = "biz_code")
    @ApiModelProperty("活动来源码")
    private String bizCode;

    @Column(name = "biz_source")
    @ApiModelProperty("业务来源：1活动领取,2手动赠送")
    private Integer bizSource;

    @Column(name = "user_id")
    @ApiModelProperty("用户ID")
    private String userId;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

}