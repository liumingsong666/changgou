package com.song.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 15:06
 * @Description:
 */

public class JwtUtil {

    private static final String PASSWORD = "song_ge";

    public static String getToken(String username,Long ttl){
        return Jwts.builder().setIssuer("zuul").claim("username",username)
                .setExpiration(new Date(System.currentTimeMillis()+ttl))
                .signWith(SignatureAlgorithm.HS256,PASSWORD).compact();
    }

    public static Claims checkToken(String token){
        return Jwts.parser().setSigningKey(PASSWORD).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        String song = getToken("song", 1000000L);
        System.out.println(song);
    }

}
