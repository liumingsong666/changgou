package com.song.control;

import com.song.entity.Constant;
import com.song.cache.CacheService;
import com.song.utils.IPUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import netscape.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/8 21:59
 * @Description:
 */

@Controller
//@RequestMapping("/login")
public class LoginController {

    @Value("${redis.image.ttl:60}")
    private long ttl;

    @Value("${redis.image.code:redis:image:code}")
    private String redisImageCode;

    @Autowired
    private AuthRequestFactory authRequestFactory;

    @GetMapping("/three/{type}")
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

    @GetMapping("/captcha")
    public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException, FontFormatException {
        //引入的第三方包
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(130, 48);
        arithmeticCaptcha.setFont(Captcha.FONT_1,30);
        arithmeticCaptcha.toBase64();
        //获取运算结果
        String text = arithmeticCaptcha.text();
        String remoteAddr = IPUtil.getIpAddress(request);
        //将验证码存入redis，以ip为限制
        redisCacheServiceImpl.setCacheInfo(redisImageCode +remoteAddr,text,ttl, TimeUnit.SECONDS);
        arithmeticCaptcha.out(response.getOutputStream());
    }

    @RequestMapping("/home")
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("/page/index.html");

    }

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_超级管理员')")
    public String test(HttpServletRequest request){
        return "你有权限查看";
    }

    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

}
