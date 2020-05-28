package com.song.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: mingsong.liu
 * @date: 2020-05-18 10:39
 * @description: 全局日志id，埋点处理
 */

@WebFilter(filterName = "logFilter", urlPatterns = "/**")
//@Order(-1)
public class LogMdcFilter implements Filter {

    private final static String GLOBAL_MSG = "CHANGGOU_MSG";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String global_msg = request.getParameter("GLOBAL_MSG");
        if(StringUtils.isEmpty(global_msg)){
           global_msg= UUID.randomUUID().toString();
        }
        String msgId = getId(global_msg);
        MDC.put(GLOBAL_MSG,msgId);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private String getId(String s){
        return String.format("%s@",s);
    }

    @Override
    public void destroy() {
        MDC.remove(GLOBAL_MSG);
    }
}
