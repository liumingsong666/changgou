package com.changgou.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 11:16
 * @Description: 将token存入redis，然后两次校验，可以添加ip作为防止令牌被盗用
 */

public class JwtUtil {

    private static final String SECRET = "song_ge_";

    private static final String ISSUER = "zuul";

    public static String getToken(String username,String Ip){
        return Jwts.builder().setIssuer(ISSUER).claim("username",username)
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(SignatureAlgorithm.HS256,SECRET+Ip).compact();
    }

    public static Claims checkToken(String token,String Ip){
        return Jwts.parser().setSigningKey(SECRET+Ip).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        String song = getToken("song", "0:0:0:0:0:0:0:1");
        System.out.println(song);
    }


}
