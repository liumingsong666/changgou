package com.song.filter;

import com.alibaba.fastjson.JSON;
import com.song.cache.CacheService;
import com.song.entity.Result;
import com.song.utils.IPUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import sun.net.util.IPAddressUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 13:17
 * @Description: ip限制过滤器
 */
@Component
@Slf4j
public class IpZuulFilter extends AbstractZuulFilter {


    @Override
    public int filterOrder() {
        return 1;
   }

    @Autowired
    private CacheService ipCacheServiceImpl;

    @Autowired
    private CacheManager cacheManager;


    @SneakyThrows
    @Override
    public Object run() {
        //获取真实ip

        HttpServletRequest request = currentContext.getRequest();
        String remoteAddr = IPUtil.getIpAddress(request);
        //RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Object cacheInfo = ipCacheServiceImpl.getCacheInfo(remoteAddr);
        String uri = request.getRequestURI();
        if (Objects.isNull(cacheInfo)) {

            if (uri.startsWith("/login") || uri.endsWith("/v2/api-docs")) {
                return null;
            }

            String remoteHost = request.getRemoteHost();
            String remoteUser = request.getRemoteUser();
            log.info("IP为：{}，地址为：{}，用户为：{}，uri: {}", remoteAddr, remoteHost, remoteUser, request.getRequestURI());

            //获取缓存中是否登录过，一定时间内登录过就 +1
            CaffeineCache other = (CaffeineCache) cacheManager.getCache("IPCount");
            Cache.ValueWrapper valueWrapper = other.get(remoteAddr+uri);
            if (valueWrapper == null) {
                other.put(remoteAddr+uri, 1);
                currentContext.setSendZuulResponse(true);
                return null;
            } else {
                Integer count = (Integer) valueWrapper.get();
                count += 1;
                log.info("登录次数:{}", count);
                if (count > 3) {
                    other.evict(remoteAddr+uri);
                    ipCacheServiceImpl.setCacheInfo(remoteAddr, remoteAddr);
                    currentContext.setSendZuulResponse(false);
                    HttpServletResponse response = currentContext.getResponse();
                    response.setContentType(MediaType.TEXT_HTML_VALUE);
                    currentContext.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), "ip次数操作过多，已被限制");
                    return null;
                }

                other.put(remoteAddr+uri, count);
                currentContext.setSendZuulResponse(true);
                return null;
            }
        }
        currentContext.setSendZuulResponse(false);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(Result.fail(HttpStatus.BAD_GATEWAY.value(),"点击次数过快，ip被限制使用")));
        return null;
    }

}
