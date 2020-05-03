package com.song.filter;


import com.song.entity.Constant;
import com.song.utils.JwtUtil;
import com.song.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    HttpSessionRequestCache httpSessionRequestCache=new HttpSessionRequestCache();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //获取缓存的初始访问地址
        SavedRequest request1 = httpSessionRequestCache.getRequest(request, response);

        User principal = (User) authentication.getPrincipal();
        String token = JwtUtil.getToken(principal.getUsername(), request.getRemoteAddr());
        //认证成功将令牌存入头部
        response.addHeader(Constant.token.TOKEN_AUTHOR,token);
        Cookie cookie = new Cookie("username", ((User)authentication.getPrincipal()).getUsername());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        //删除redis中的验证码
        String remoteAddr = request.getRemoteAddr();
        try {
            //删除失败不影响主逻辑
            redisCacheServiceImpl.deleteCacheInfo(Constant.redis.REDIS_IMAGE_CODE+remoteAddr);

        }catch (Exception e){
            log.info("删除redis的图形验证码失败",e);

        }finally {

            if(request1 != null){
                String redirectUrl = request1.getRedirectUrl();
//                DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
//                //request.getSession().invalidate();
//                defaultRedirectStrategy.sendRedirect(request,response,redirectUrl );
                response.sendRedirect(redirectUrl);
                return;
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
