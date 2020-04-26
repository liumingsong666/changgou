package com.song.filter;

import com.changgou.entity.Constant;
import com.changgou.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 15:01
 * @Description: 令牌限制过滤器
 */
@Component
@Slf4j
public class TokenFilter extends AbstractZuulFilter {


    @Override
    public int filterOrder() {
        return 2;
    }

    @SneakyThrows
    @Override
    public Object run() {

        HttpServletRequest request = currentContext.getRequest();
        String author = request.getHeader(Constant.token.TOKEN_AUTHOR);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        String uri = request.getRequestURI();

        if (this.uri.contains(uri)) {
            return null;
        }
        if (Objects.isNull(author)) {
            response.sendRedirect("/page/index.html");
            //response.sendRedirect("http://localhost:8200/page/index.html");
            currentContext.setSendZuulResponse(false);
            return null;
        }
        try {
            String remoteAddr = request.getRemoteAddr();
            JwtUtil.checkToken(author,remoteAddr);
            currentContext.setSendZuulResponse(true);
        } catch (ExpiredJwtException e) {
            currentContext.setSendZuulResponse(false);
            //response.sendRedirect("/login/changgou");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "令牌过期");
        } catch (Exception e) {
            currentContext.setSendZuulResponse(false);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "令牌错误");
        }
        return null;
    }
}
