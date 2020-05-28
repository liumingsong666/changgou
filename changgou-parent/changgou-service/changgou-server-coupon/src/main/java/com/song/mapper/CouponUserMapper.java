package com.song.mapper;

import com.song.entity.CouponUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户领卷表(CouponUser)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-24 20:39:37
 */
public interface CouponUserMapper {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    CouponUser queryById(Integer id);

    /**
     * 查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CouponUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     * @param couponUser 实例对象
     * @return 对象列表
     */
    List<CouponUser> queryAll(CouponUser couponUser);

    /**
     * 新增数据
     * @param couponUser 实例对象
     * @return 影响行数
     */
    int insert(CouponUser couponUser);

    /**
     * 修改数据
     * @param couponUser 实例对象
     * @return 影响行数
     */
    int update(CouponUser couponUser);

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}