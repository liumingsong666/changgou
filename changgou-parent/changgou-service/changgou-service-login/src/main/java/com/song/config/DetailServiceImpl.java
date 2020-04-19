package com.song.config;

import com.changgou.entity.UserInfo;
import com.google.common.collect.Lists;
import com.song.control.UserRPC;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 23:47
 * @Description:
 */

@Slf4j
public class DetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRPC userRPC;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserInfo userInfo = userRPC.queryUser(s);
        if(Objects.isNull(userInfo)){
            throw new UsernameNotFoundException("用户不存在");
        }
//        String encode = bCryptPasswordEncoder.encode(userInfo.getPassword());
//        log.info("加密后的密码：{}",encode);
        return new User(s,userInfo.getPassword(), Lists.newArrayList(new SimpleGrantedAuthority("Admin")));
    }

}
