package com.song.service;

import com.song.entity.User;
import com.song.mapper.UserMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户表(User)表服务实现类
 * @author mingsong.liu
 * @since 2020-04-13 00:28:05
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Cacheable(value = "user",key="#id",unless = "#result==null or #result=='' ")
    public User queryById(Integer id) {
        System.out.println("走redis查询");
        Object o =  redisTemplate.opsForValue().get(String.valueOf(id));
        if(Objects.nonNull(o)){
            return (User) o;
        }
        System.out.println("走数据库查询");
        User user = this.userMapper.queryById(id);
        redisTemplate.opsForValue().set("REDIS_USER_"+id,user,10, TimeUnit.SECONDS);
        //return userMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 查询多条数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     * @param user 实例对象
     * @return 实例对象
     */
    public User insert(User user) {
        this.userMapper.insert(user);
        return user;
    }

    /**
     * 修改数据
     * @param user 实例对象
     * @return 实例对象
     */
    public User update(User user) {
        this.userMapper.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id) {
        return this.userMapper.deleteById(id) > 0;
    }
}