package com.song.filter;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.song.entity.Constant;
import com.song.entity.LoginLog;
import com.song.entity.Result;
import com.song.entity.UserInfo;
import com.song.mapper.LoginLogMapper;
import com.song.utils.IPUtil;
import com.song.utils.JwtUtil;
import com.song.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 22:36
 * @Description:
 */

@Component
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Autowired
    private CacheService redisCacheServiceImpl;
    //获取缓存地址，前后端分离，前端跳转
    //HttpSessionRequestCache httpSessionRequestCache=new HttpSessionRequestCache();

    @Autowired
    private LoginLogMapper loginLogMapper;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //获取缓存的初始访问地址,通过前端传过来
        //SavedRequest request1 = httpSessionRequestCache.getRequest(request, response);

        //存放信息在token中
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        String ipAddr = IPUtil.getIpAddress(request);
        Map<String, Object> map = Maps.newHashMap();
        map.put("nickName", userInfo.getNickName());
        map.put("username", userInfo.getUsername());
        String token = JwtUtil.getToken(map);

        //认证成功将令牌存入头部
        response.addHeader(Constant.token.TOKEN_AUTHOR, token);
        Cookie cookie = new Cookie("nickName", userInfo.getNickName());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        Cookie tokenCookie = new Cookie(Constant.token.TOKEN_AUTHOR, token);
        tokenCookie.setMaxAge(3600 * 24);
        response.addCookie(tokenCookie);

        //删除redis中的验证码
        try {
            //登录日志记录
            executorService.execute(() -> {

                LoginLog loginLog = LoginLog.builder().customerId(userInfo.getId())
                        .loginIp(ipAddr).loginType(1).build();
                int i = loginLogMapper.insertSelective(loginLog);
                log.info("登录记录表插入 {}",i==1?"成功":"失败");
            });

            //删除失败不影响主逻辑
            redisCacheServiceImpl.deleteCacheInfo(Constant.redis.REDIS_IMAGE_CODE + ipAddr);

        } catch (Exception e) {
            log.info("删除redis的图形验证码失败", e);

        } finally {

            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(JSON.toJSONString(Result.success("登录成功", token)));
        }

    }
}
