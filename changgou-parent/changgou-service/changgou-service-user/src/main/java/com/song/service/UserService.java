package com.song.service;

import com.alibaba.fastjson.JSON;
import com.song.entity.Constant;
import com.song.entity.Result;
import com.song.entity.User;
import com.song.mapper.UserMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户表(User)表服务实现类
 * @author mingsong.liu
 * @since 2020-04-13 00:28:05
 */
@Service
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
    public Result<User> queryById(Integer id) {
        System.out.println("走redis查询");
        Object o =  redisTemplate.opsForValue().get("REDIS_USER_"+ id);
        if(Objects.nonNull(o)){
            return Result.success(o);
        }
        System.out.println("走数据库查询");
        User user = this.userMapper.queryById(id);
        redisTemplate.opsForValue().set("REDIS_USER_"+id,user,100, TimeUnit.SECONDS);
        //return userMapper.selectByPrimaryKey(id);
        return Result.success(user);
    }

    /**
     * 查询多条数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public Result<List<User>> queryAllByLimit(int offset, int limit) {
        return Result.success(userMapper.queryAllByLimit(offset, limit));
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public Result<Void> deleteById(Integer id) {

        if(userMapper.deleteById(id)>0){
            return Result.success(null);
        }
        return Result.fail(HttpStatus.BAD_REQUEST.value(),"删除失败");
    }


    public Result<User> queryByNickName(String nickName) {
        User user = userMapper.queryByName(nickName);
        return Result.success(user);
    }

    /**
     * 通过对象查询用户
     * @param user
     * @return
     */
    public Result<User> queryByUser(User user) {
        User user1 = userMapper.selectOne(user);
        return Result.success(user1);
    }


    public Result<User> queryByPhone(String phone) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession(false);
        System.out.println(session);
        HttpSession session1 = request.getSession();
        System.out.println(session1.getId()+"==="+session1.getMaxInactiveInterval());
        User user = userMapper.queryByPhone(phone);
        return Result.success(user);
    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(1589264160000L)));
    }
}