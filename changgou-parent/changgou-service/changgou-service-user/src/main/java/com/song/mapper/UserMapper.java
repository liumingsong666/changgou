package com.song.mapper;

import com.song.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-04-13 00:28:07
 */

@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    User queryById(@Param("id") Integer id);

    /**
     * 查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);


    /**
     * 修改数据
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过用户名查询
     * @param nickName
     * @return
     */
    User queryByName(@Param("nickName") String nickName);

    /**
     * @author: mingsong.liu
     * @time: 2020-05-10 13:22
     * @description: 通过手机获取对象
     *
     */
    User queryByPhone(@Param("phone") String phone);
}