package com.song.filter;

import com.alibaba.fastjson.JSON;
import com.song.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-22 17:29
 * @Description: 没有权限的处理器
 */

@Component
public class AuthorHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result result = new Result(false, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
