package com.song.interceptor;

import com.alibaba.fastjson.JSON;
import com.song.entity.Constant;
import com.song.entity.Result;
import com.song.utils.IPUtil;
import com.song.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 17:17
 * @description: 生成请求上下文
 */


public class TokenInterceptor implements HandlerInterceptor {

    public static ThreadLocal<String> contextUsername = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constant.token.TOKEN_AUTHOR);
        Claims claims ;
        try {
             claims=JwtUtil.checkToken(token);
        }catch (Exception e){
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JSON.toJSONString(Result.fail(HttpStatus.UNAUTHORIZED.value(),"没有登录")));
            return false;
        }

        String username = (String) claims.get("username");
        contextUsername.set(username);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        contextUsername.remove();
    }
}
