package com.song.dao;


import com.song.entity.OrderCart;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * 购物车表(OrderCart)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-09 11:11:15
 */

public interface OrderCartMapper extends BaseMapper<OrderCart> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderCart queryById(Integer id);
    

}