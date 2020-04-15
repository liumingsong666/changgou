package com.changgou.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 11:16
 * @Description:
 */

public class JwtUtil {

    public static String generatToken(String username,String key,Long ttl ){
        return Jwts.builder().claim("username",username)
                .setExpiration(new Date(System.currentTimeMillis()+ttl))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key).compact();
    }

    public static Claims checkToken(String token,String key){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }


}
