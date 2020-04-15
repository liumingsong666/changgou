package com.song.filter;

import com.song.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 15:01
 * @Description:
 */
@Component
@Slf4j
public class TokenFilter extends AbstractZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @SneakyThrows
    @Override
    public Object run() {

        HttpServletRequest request = currentContext.getRequest();
        String author = request.getHeader("Author");
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        if (uri.contains(request.getRequestURI())) {
            return true;
        }
        if (Objects.isNull(author)) {
            currentContext.setSendZuulResponse(false);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("没有获取token");
            return null;
        }
        try {
            JwtUtil.checkToken(author);
            currentContext.setSendZuulResponse(true);
        } catch (ExpiredJwtException e) {
            currentContext.setSendZuulResponse(false);
            response.getWriter().write("ssss");
            //response.sendError(HttpStatus.UNAUTHORIZED.value(), "令牌过期");
            return null;
        } catch (Exception e) {
            currentContext.setSendZuulResponse(false);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "令牌错误");
            return null;
        }
        return null;
    }
}
