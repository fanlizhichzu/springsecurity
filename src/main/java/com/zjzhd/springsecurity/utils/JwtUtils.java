package com.zjzhd.springsecurity.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * @author fanlz
 * @date 2021-04-15 13:45
 **/
public class JwtUtils {

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param authorities
     * @param ttlMillis
     * @return String
     */
    public static String createJWT(String id, String subject, String authorities, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .claim("authorities", authorities)
//                .setIssuer(role)
                .setIssuedAt(now)
                .signWith(key);
        if (ttlMillis >= 0) {
            // 设置过期时间
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static String validateJWT(String jwtStr) {
        try {
            Claims claims = parseJWT(jwtStr);
            Date expiration = claims.getExpiration();
            if (new Date().before(expiration)) {
                return claims.getSubject();
            } else {
                // 过期了
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
