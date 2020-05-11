package com.song.vo;

import com.song.entity.OrderCart;
import com.song.entity.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: mingsong.liu
 * @date: 2020-05-09 14:02
 * @description: 显示前端的数据
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("展示前端的购物车详情")
public class OrderCartVo {


    @ApiModelProperty(value = "商品详情")
    private ProductInfo productInfo;


    @ApiModelProperty(value = "加入购物车商品数量", dataType = "Integer")
    private Integer productAmount;


}
