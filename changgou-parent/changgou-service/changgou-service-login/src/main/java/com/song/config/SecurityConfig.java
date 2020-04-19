package com.song.config;

import com.song.filter.ImageCodeFilter;
import com.song.filter.LoginFailHandler;
import com.song.filter.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 22:30
 * @Description:
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private ImageCodeFilter imageCodeFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/fonts/**");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/page/index.html").loginProcessingUrl("/login/changgou")
                .successHandler(loginSuccessHandler).failureHandler(loginFailHandler)
                .failureForwardUrl("/page/index.html")
                .and()
                .authorizeRequests().antMatchers("/page/index.html","/login/**","/css/**","/js/**","/img/**","/fonts/**","/login/captcha","/wx/callback").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
        super.configure(http);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new DetailServiceImpl();
    }
}
