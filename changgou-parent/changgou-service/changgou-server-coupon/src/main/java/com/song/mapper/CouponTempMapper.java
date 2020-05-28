package com.song.mapper;

import com.song.entity.CouponTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券模板(CouponTemp)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-24 20:39:37
 */

@Repository
public interface CouponTempMapper {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    CouponTemp queryById(Integer id);

    /**
     * 查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CouponTemp> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     * @param couponTemp 实例对象
     * @return 对象列表
     */
    List<CouponTemp> queryAll(CouponTemp couponTemp);

    /**
     * 获取所有的优惠券
     * @return
     */
    List<CouponTemp> queryAll();

    /**
     * 新增数据
     * @param couponTemp 实例对象
     * @return 影响行数
     */
    int insert(CouponTemp couponTemp);

    /**
     * 修改数据
     * @param couponTemp 实例对象
     * @return 影响行数
     */
    int update(CouponTemp couponTemp);

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    int stopTempById(@Param("couponTempId") String couponTempId);

    /**
     * 获取可用的优惠券模板
     * @return
     */
    List<CouponTemp> getUsableTemp();

    CouponTemp queryByTempId(@Param("tempId") String tempId);
}