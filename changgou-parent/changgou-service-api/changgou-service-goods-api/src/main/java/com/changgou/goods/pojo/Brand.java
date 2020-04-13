package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tb_brand")
@ApiModel("品牌表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "自增id")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("品牌名字")
    private String name;

    @Column(name = "image")
    @ApiModelProperty("品牌图片地址")
    private String image;

    @Column(name = "letter")
    @ApiModelProperty("首字母")
    private String letter;

    @Column(name = "seq")
    private Integer seq;
}
