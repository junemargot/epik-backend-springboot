package com.everyplaceinkorea.epik_boot3_api.auth.filter;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter_v2 extends OncePerRequestFilter {

//    @Getter
//    @Value("rland.jwt.secret")
//    private String secret;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter_v2(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 쿠키에서 JWT 토큰을 추출
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt_token")) {
                    token = cookie.getValue();
                    System.out.println("쿠키 겟차");
                    break;
                }
            }
        }else
            System.out.println("쿠키 없다 ㅜㅜ");

        // JWT 토큰이 존재하고 유효하다면
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            System.out.println(username); // 토큰 확인용 코트
            String email = jwtUtil.extractEmail(token); // 토큰 확인용 코드
            System.out.println(email); // 토큰 확인용 코트
            List<String> roles = jwtUtil.extractRoles(token);

            // 인증 정보를 담는다
            if (username != null && !username.isEmpty()) {
                // 사용자 정보와 역할(role)을 가져와서 인증 객체를 생성
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UserDetails userDetails = EpikUserDetails.builder()
                        .username(username)
                        .authorities(authorities)
                        .build();

                // 인증된 사용자 정보를 Spring Security의 SecurityContext에 설정
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response); // 다음 필터에게 전달
    }
    }



