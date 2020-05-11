package com.song.dao;

import com.song.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Set;


/**
 * 商品信息表(ProductInfo)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-09 11:11:15
 */

@Repository
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    /**
     * 通过ID查询单条数据
     *
     * @param productId 主键
     * @return 实例对象
     */
    ProductInfo queryById(Object productId);

    List<ProductInfo> queryByIds(@Param("ids") Set<Integer> ids);

}