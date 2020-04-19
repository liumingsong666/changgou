package com.song.filter;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 23:10
 * @Description:
 */

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print(JSON.toJSONString(new Result<>(false, StatusCode.ERROR,exception.getMessage())));
    }
}
