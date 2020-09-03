package com.springbootapp.coloniaweb.configurations.security.JWT;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtil {

    public String username(String token) {
        return claim(token, Claims::getSubject);
    }

    public Date expiration(String token) {
        return claim(token, Claims::getExpiration);
    }

    public <T> T claim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = allClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims allClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(JwtConstants.SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    public String token(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(userDetails.getUsername(), claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = username(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    private String createToken(String subject, Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)).setId(UUID.randomUUID().toString())
                .signWith(JwtConstants.SECRET_KEY).compact();
    }

    private Boolean isTokenExpired(String token) {
        return expiration(token).before(new Date());
    }

}