package com.song.config;

import com.google.common.collect.Lists;
import com.song.control.UserRPC;
import com.song.dao.RoleDao;
import com.song.entity.Role;
import com.song.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

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

    @Autowired
    private RoleDao roleDao;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = userRPC.queryUser(s);
        if(Objects.isNull(userInfo)){
            throw new UsernameNotFoundException("用户不存在");
        }
        Role roleDto = roleDao.selectByPrimaryKey(userInfo.getId());
        if(Objects.isNull(roleDto) || StringUtils.isEmpty(roleDto.getName())){
            throw new InternalAuthenticationServiceException("用户权限没有找到");
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_"+roleDto.getName());

//        String encode = bCryptPasswordEncoder.encode(userInfo.getPassword());
//        log.info("加密后的密码：{}",encode);
        return new User(s,userInfo.getPassword(), Lists.newArrayList(simpleGrantedAuthority));
    }

}
