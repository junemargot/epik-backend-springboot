package com.everyplaceinkorea.epik_boot3_api.auth.filter;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter_v2 extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter_v2(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 쿠키에서 JWT 토큰을 추출
        String token = null;
        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if("jwt_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // JWT 토큰이 존재하고 유효하면 사용자 인증 정보 설정
        if(token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            List<String> roles = jwtUtil.extractRoles(token);

            if(username != null && !username.isEmpty()) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for(String role : roles) {
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

        // 다음 필터 체인으로 요청 전달
        filterChain.doFilter(request, response);
    }
}



