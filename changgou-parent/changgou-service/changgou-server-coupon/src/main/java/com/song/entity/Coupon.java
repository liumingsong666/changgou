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
 * @description 优惠券主表(coupon)表实体类
 * @date 2020-05-24 20:39:37
 */

@Table(name = "coupon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("优惠券主表")
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty("id")
    private Integer id;

    @Column(name = "temp_id")
    @ApiModelProperty("模板id")
    private String tempId;

    @Column(name = "coupon_id")
    @ApiModelProperty("优惠券id")
    private String couponId;

    @Column(name = "coupon_name")
    @ApiModelProperty("优惠券名称")
    private String couponName;

    @Column(name = "coupon_desc")
    @ApiModelProperty("优惠券描述")
    private String couponDesc;

    @Column(name = "coupon_status")
    @ApiModelProperty("优惠券状态 1：未领取 2：已领取,未使用  3：已使用 4：已过期 5：已冻结 6：已作废")
    private Object couponStatus;

    @Column(name = "coupon_type")
    @ApiModelProperty("优惠券类型 1：满减卷 2: 折扣券 3：立减券")
    private Object couponType;

    @Column(name = "coupon_money_min")
    @ApiModelProperty("最少使用金额 0：不限制(单位：元,依赖coupon_type)")
    private Double couponMoneyMin;

    @Column(name = "coupon_money_max")
    @ApiModelProperty("最大使用金额 0：不限制(单位：元,依赖coupon_type)")
    private Double couponMoneyMax;

    @Column(name = "coupon_value")
    @ApiModelProperty("优惠券面值,小于0为折扣")
    private Double couponValue;

    @Column(name = "coupon_start_time")
    @ApiModelProperty("优惠券开始时间")
    private Date couponStartTime;

    @Column(name = "coupon_end_time")
    @ApiModelProperty("优惠券结束时间")
    private Date couponEndTime;

    @Column(name = "draw_time")
    @ApiModelProperty("领取时间")
    private Date drawTime;

    @Column(name = "use_time")
    @ApiModelProperty("使用时间")
    private Date useTime;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

}