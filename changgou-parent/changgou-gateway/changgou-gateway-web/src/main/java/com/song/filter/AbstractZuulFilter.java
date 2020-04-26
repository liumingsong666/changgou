package com.song.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 13:13
 * @Description:
 */


public abstract class AbstractZuulFilter extends ZuulFilter {


    protected RequestContext currentContext;

    protected final List<String> uri = Arrays.asList("/login","login/changgou","/websocket", "/page/index.html","/druid", "/zipkin", "/eureka");


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }


    @Override
    public boolean shouldFilter() {
        currentContext = RequestContext.getCurrentContext();
        return currentContext.sendZuulResponse();
    }


}
