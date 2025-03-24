package com.everyplaceinkorea.epik_boot3_api.auth.util;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${epik.jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // 유효성 검사를 위해 클레임을 추출
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    //롤 테스트
    public List<String> extractRoles(String token) {
        List<Map<String, String>> roles = extractAllClaims(token).get("role", List.class);

        List<String> roleNames = new ArrayList<>();
        for(Map<String, String> role : roles) {
            System.out.println(role);
            roleNames.add(role.get("authority"));
            System.out.println(role.get("authority"));
        }
        return roleNames;
    }




    public Long extractId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(EpikUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("role", userDetails.getAuthorities());
        claims.put("nickname", userDetails.getNickname());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

    }
}