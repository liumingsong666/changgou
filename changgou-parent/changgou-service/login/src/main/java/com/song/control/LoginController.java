package com.song.control;

import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:59
 * @Description:
 */

@Controller
public class LoginController {

    @Autowired
    private AuthRequestFactory authRequestFactory;

    @GetMapping("/login/{type}")
    public void login(@PathVariable("type") String type , HttpServletResponse response) throws IOException {
        AuthRequest authRequest = authRequestFactory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
        return ;
    }

    @RequestMapping("/wx/callback")
    @ResponseBody
    public AuthResponse login(AuthCallback authCallback){
        AuthRequest wechat_open = authRequestFactory.get("WECHAT_OPEN");
        AuthResponse login = wechat_open.login(authCallback);
        return login;
    }

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("redis")
    public String test(){
        redisTemplate.opsForValue().set("kkk","kkk");
        return "success";
    }
}
