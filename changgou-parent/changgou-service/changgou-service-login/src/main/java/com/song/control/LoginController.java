package com.song.control;

import com.changgou.entity.Constant;
import com.netflix.discovery.converters.Auto;
import com.song.cache.CacheService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:59
 * @Description:
 */

@Controller
public class LoginController {

    @Autowired
    private AuthRequestFactory authRequestFactory;

    @GetMapping("/login/three/{type}")
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
    private CacheService redisCacheServiceImpl;

    @GetMapping("/login/captcha")
    public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException, FontFormatException {
        //引入的第三方包
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(130, 48);
        arithmeticCaptcha.setFont(Captcha.FONT_1,30);
        arithmeticCaptcha.toBase64();
        //获取运算结果
        String text = arithmeticCaptcha.text();
        String remoteAddr = request.getRemoteAddr();
        //将验证码存入redis，以ip为限制
        redisCacheServiceImpl.setCacheInfo(Constant.redis.REDIS_IMAGE_CODE +remoteAddr,text,Constant.redis.REDIS_IMAGE_TTL, TimeUnit.SECONDS);
        arithmeticCaptcha.out(response.getOutputStream());
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/page/index.html").forward(request,response);
        return;
    }

}
