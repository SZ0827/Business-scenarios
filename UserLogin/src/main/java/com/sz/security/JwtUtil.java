package com.sz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private final String SECRET_KEY="yourSecretKey";
    public String generateToken(UserDetails userDetails) {
        Map<String ,Object> claims=new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256,SECRET_KEY)
            .compact();
    }
    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从 Token 中提取到期时间
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
//解析和验证JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 Token 是否过期
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
