package com.song.filter;

import com.alibaba.fastjson.JSON;
import com.song.entity.Constant;
import com.song.entity.Result;
import com.song.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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
        HttpServletResponse response = currentContext.getResponse();
        String uri = request.getRequestURI();

        if (this.uri.contains(uri)) {
            return null;
        }
        if (uri.startsWith("/login") || uri.endsWith("/v2/api-docs")) {
            return null;
        }

        String token = request.getHeader(Constant.token.TOKEN_AUTHOR);
        if (Objects.isNull(token)) {
            String reUri = "/login/page/index.html?redirect_uri=" + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
            currentContext.setSendZuulResponse(false);
            response.sendRedirect(reUri);

            return null;
        }


        try {
            JwtUtil.checkToken(token);
            currentContext.setSendZuulResponse(true);
        } catch (ExpiredJwtException e) {
            currentContext.setSendZuulResponse(false);
            Result result = Result.fail(HttpStatus.BAD_GATEWAY.value(), "令牌过期");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (Exception e) {
            currentContext.setSendZuulResponse(false);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "令牌错误");
        }
        return null;
    }
}
