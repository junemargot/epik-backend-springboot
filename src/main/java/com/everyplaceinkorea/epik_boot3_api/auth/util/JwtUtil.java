package com.everyplaceinkorea.epik_boot3_api.auth.util;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    private final Key secretKey;
    private final long tokenValidityInMilliseconds = 1000 * 60 * 60 * 10; // 10시간

    public JwtUtil(@Value("${epik.jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            log.error("잘못되거나 만료된 JWT 토큰: {}", e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public List<String> extractRoles(String token) {
        List<Map<String, String>> roles = extractAllClaims(token).get("role", List.class);

        if(roles == null) return Collections.emptyList();

        return roles.stream()
                .map(role -> role.get("authority"))
                .collect(Collectors.toList());
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
        claims.put("nickname", userDetails.getNickname());
        claims.put("profileImage", userDetails.getProfileImage());
        claims.put("role", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMilliseconds))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // JWT 리프레시 토큰 생성
    public String generateRefreshToken(EpikUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMilliseconds))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}