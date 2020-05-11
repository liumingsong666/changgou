package com.song.interceptor;

import com.song.entity.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: mingsong.liu
 * @date: 2020-05-08 11:02
 * @description:
 */
@Component
public class TokenRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader(Constant.token.TOKEN_AUTHOR);
        if(StringUtils.isNotBlank(token)){
            requestTemplate.header(Constant.token.TOKEN_AUTHOR,token);
        }
    }
}
