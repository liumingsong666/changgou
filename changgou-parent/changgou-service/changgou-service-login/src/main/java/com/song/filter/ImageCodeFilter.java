package com.song.filter;

import com.song.entity.Constant;
import com.song.cache.CacheService;
import com.song.utils.IPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.net.util.IPAddressUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-18 11:48
 * @Description:
 */

public class ImageCodeFilter extends OncePerRequestFilter {


    private CacheService redisCacheServiceImpl;

    private LoginFailHandler loginFailHandler;

    private String redisImageCode;


    public ImageCodeFilter(CacheService redisCacheServiceImpl,LoginFailHandler loginFailHandler,String redisImageCode){
        this.redisCacheServiceImpl=redisCacheServiceImpl;
        this.loginFailHandler=loginFailHandler;
        this.redisImageCode=redisImageCode;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String imageCode = httpServletRequest.getParameter("imageCode");
        String remoteAddr = IPUtil.getIpAddress(httpServletRequest);
        String uri = httpServletRequest.getRequestURI();
        //存放之前的登录地址
        String refererUri = httpServletRequest.getHeader("Referer");
        //todo 无法在session禁用的时候缓存初始地址
        HttpSession session = httpServletRequest.getSession(false);

        if("/changgou".equals(uri) && "POST".equals(httpServletRequest.getMethod())){
            Object cacheInfo = redisCacheServiceImpl.getCacheInfo(redisImageCode + remoteAddr);
            try {
                if(StringUtils.isEmpty(imageCode)){
                    throw new InternalAuthenticationServiceException("验证码为空");
                }
                if(Objects.isNull(cacheInfo)){
                    throw new InternalAuthenticationServiceException("验证码过期，请重新刷新");
                }
                String imageCodeCache = (String) cacheInfo;
                if(!imageCode.equals(imageCodeCache)){
                    throw new InternalAuthenticationServiceException("验证码输入错误，请重新输入");
                }

            }catch (AuthenticationException e){
                loginFailHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
