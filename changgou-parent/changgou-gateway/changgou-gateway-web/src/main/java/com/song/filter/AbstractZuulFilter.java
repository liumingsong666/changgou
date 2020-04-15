package com.song.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 13:13
 * @Description:
 */


public abstract class AbstractZuulFilter extends ZuulFilter {

    public  RequestContext currentContext = RequestContext.getCurrentContext() ;

    public  List<String> uri = Arrays.asList("/login","/druid","/zipkin","/eureka");


    @Override
    public boolean shouldFilter() {
        currentContext=RequestContext.getCurrentContext();
        return currentContext.sendZuulResponse();
    }


}
