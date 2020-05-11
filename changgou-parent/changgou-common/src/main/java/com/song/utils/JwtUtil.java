package com.song.utils;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 11:16
 * @Description: 将token存入redis，然后两次校验，可以添加ip作为防止令牌被盗用
 */

public class JwtUtil {

    private static final String SECRET = "song_ge_";

    private static final String ISSUER = "zuul";

    public static String getToken(Map<String,Object> claims){
        return Jwts.builder().setIssuer(ISSUER).addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
    }

    public static Claims checkToken(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("nickName","刘小铭");
        map.put("phone",10086);
        String song = getToken(map);
        System.out.println(song);
    }


}
