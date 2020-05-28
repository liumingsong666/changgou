package com.song.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @description 优惠券模板(coupon_temp)表实体类
 * @date 2020-05-24 20:39:37
 */
@Table(name = "coupon_temp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("优惠券模板")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponTemp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty("id")
    private Integer id;

    @Column(name = "temp_id")
    @ApiModelProperty("优惠券码")
    private String tempId;

    @Column(name = "coupon_name")
    @ApiModelProperty("优惠券名称")
    private String couponName;

    @Column(name = "coupon_desc")
    @ApiModelProperty("优惠券描述")
    private String couponDesc;

    @Column(name = "temp_status")
    @ApiModelProperty("优惠券状态 1：未启用 2：已启用 3：已中止 4：已过期 5 生成中")
    private Object tempStatus;

    @Column(name = "coupon_num")
    @ApiModelProperty("优惠券数量")
    @NotNull(message = "优惠券数量不能为空")
    private Integer couponNum;

    @Column(name = "limit_type")
    @ApiModelProperty("1 固定时间范围过期，2 领券后固定时间过期")
    private Integer limitType;

    @Column(name = "limit_day")
    @ApiModelProperty("优惠券限制使用天数:0不限制")
    private Integer limitDay;

    @Column(name = "coupon_type")
    @ApiModelProperty("优惠券类型 1：满减卷 2: 折扣券 3:立减券")
    @NotNull(message = "优惠券类型不能为空")
    private Integer couponType;

    @Column(name = "coupon_money_min")
    @ApiModelProperty("最少使用金额 0：不限制(单位：元,依赖coupon_type)")
    private Double couponMoneyMin;

    @Column(name = "coupon_money_max")
    @ApiModelProperty("最大使用金额 0：不限制(单位：元,依赖coupon_type)")
    private Double couponMoneyMax;

    @Column(name = "coupon_value")
    @ApiModelProperty("优惠券面值,小于0为折扣")
    @NotNull(message = "优惠券面值不能为空")
    private Double couponValue;

    @Column(name = "coupon_start_time")
    @ApiModelProperty("优惠券开始时间")
    private Date couponStartTime;

    @Column(name = "coupon_end_time")
    @ApiModelProperty("优惠券结束时间")
    private Date couponEndTime;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

}