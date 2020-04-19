package com.song.filter;

import com.changgou.entity.Constant;
import com.song.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-18 11:48
 * @Description:
 */
@Component
public class ImageCodeFilter extends OncePerRequestFilter {

    @Autowired
    private CacheService redisCacheServiceImpl;


    @Autowired
    private LoginFailHandler loginFailHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String imageCode = httpServletRequest.getParameter("imageCode");
        String remoteAddr = httpServletRequest.getRemoteAddr();
        if("/login/changgou".equals(httpServletRequest.getRequestURI()) && "POST".equals(httpServletRequest.getMethod())){
            Object cacheInfo = redisCacheServiceImpl.getCacheInfo(Constant.redis.REDIS_IMAGE_CODE + remoteAddr);
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
