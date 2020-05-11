package com.song.mapper;

import com.song.entity.LoginLog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 用户登陆日志表(LoginLog)表数据库访问层
 *
 * @author mingsong.liu
 * @since 2020-05-10 14:24:21
 */

public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 通过ID查询单条数据
     * @param loginId 主键
     * @return 实例对象
     */
    LoginLog queryById(Long loginId);

    /**
     * 查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LoginLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     * @param loginLog 实例对象
     * @return 对象列表
     */
    List<LoginLog> queryAll(LoginLog loginLog);

    /**
     * 新增数据
     * @param loginLog 实例对象
     * @return 影响行数
     */
    int insertAll(@Param("loginLog") LoginLog loginLog);


    /**
     * 通过主键删除数据
     * @param loginId 主键
     * @return 影响行数
     */
    int deleteById(Long loginId);

}