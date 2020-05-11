package com.song.dao;

import com.song.entity.OrderMaster;
import tk.mybatis.mapper.common.BaseMapper;


/**
 * 订单主表(OrderMaster)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-09 11:11:15
 */
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderMaster queryById(Integer id);
    

}