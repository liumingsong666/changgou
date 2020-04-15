package com.song.filter;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.song.cache.CacheService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import sun.print.IPPPrintService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 13:17
 * @Description:
 */
@Component
@Slf4j
public class IpZuulFilter extends AbstractZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Autowired
    private CacheService IpCacheServiceImpl;

    @Autowired
    private CacheManager cacheManager;

    @SneakyThrows
    @Override
    public Object run() {

        HttpServletRequest request = currentContext.getRequest();
        String remoteAddr = request.getRemoteAddr();
        //RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Object cacheInfo = IpCacheServiceImpl.getCacheInfo(remoteAddr);
        System.out.println(Optional.ofNullable(cacheInfo).isPresent());
        if (Objects.isNull(cacheInfo)) {

            String remoteHost = request.getRemoteHost();
            String remoteUser = request.getRemoteUser();
            log.info("IP为：{}，地址为：{}，用户为：{}，uri: {}", remoteAddr, remoteHost, remoteUser, request.getRequestURI());

            CaffeineCache other = (CaffeineCache) cacheManager.getCache("Other");
            Cache.ValueWrapper valueWrapper = other.get(remoteAddr);
            if (valueWrapper == null) {
                other.put(remoteAddr, 1);
                currentContext.setSendZuulResponse(true);
                return null;
            } else {
                Integer count = (Integer) valueWrapper.get();
                count += 1;
                if (count>3) {
                    other.evict(remoteAddr);
                    IpCacheServiceImpl.setCacheInfo(remoteAddr, remoteAddr);
                    currentContext.setSendZuulResponse(false);
                    HttpServletResponse response = currentContext.getResponse();
                    response.setContentType(MediaType.TEXT_HTML_VALUE);
                    currentContext.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), "ip次数操作过多，已被限制");
                    return null;
                }

                other.put(remoteAddr, count);
                currentContext.setSendZuulResponse(true);
                return null;
            }
        }
        currentContext.setSendZuulResponse(false);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        currentContext.getResponse().sendError(HttpStatus.BAD_REQUEST.value(),"ip限制");
        return null;
    }

}
