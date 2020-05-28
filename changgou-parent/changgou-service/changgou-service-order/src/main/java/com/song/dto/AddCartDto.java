package com.song.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 18:13
 * @description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("购物车修改商品数量类")
public class AddCartDto implements Serializable {

    @NotNull(message = "商品不能为空")
    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品更改数量")
    private Integer count = 1;


}
