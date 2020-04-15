package com.song.twt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-15 09:15
 * @Description:
 */
@RunWith(SpringRunner.class)
public class JwtTest {

    @Test
    public void getToken() {
        String compact = Jwts.builder().setIssuer("松哥").setIssuedAt(new Date())
                .setSubject("登录").claim("user","xxx")
                .signWith(SignatureAlgorithm.HS256, "song").compact();
        System.out.println(compact);
        Claims song = Jwts.parser().setSigningKey("song").parseClaimsJws(compact).getBody();
        System.out.println(song.get("user"));

    }
}
