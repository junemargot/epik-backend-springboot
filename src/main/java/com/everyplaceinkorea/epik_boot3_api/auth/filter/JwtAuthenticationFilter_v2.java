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
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter_v2 extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 쿠키에서 JWT 토큰 추출
        String token = extractTokenFromCookie(request);

        if (token != null || jwtUtil.validateToken(token)) {
            try {
                String username = jwtUtil.extractUsername(token);
                Long id = jwtUtil.extractId(token);
                String email = jwtUtil.extractEmail(token);
                List<String> roles = jwtUtil.extractRoles(token);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UserDetails userDetails = EpikUserDetails.builder()
                        .id(id)
                        .username(username)
                        .email(email)
                        .authorities(authorities)
                        .build();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                log.error("JWT 토큰 검증 중 오류 발생: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }
//        // JWT 토큰이 존재하고 유효하면 사용자 인증 정보 설정
//        if(token != null && jwtUtil.validateToken(token)) {
//            String username = jwtUtil.extractUsername(token);
//            List<String> roles = jwtUtil.extractRoles(token);
//
//            if(username != null && !username.isEmpty()) {
//                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                for(String role : roles) {
//                    authorities.add(new SimpleGrantedAuthority(role));
//                }
//
//                UserDetails userDetails = EpikUserDetails.builder()
//                        .username(username)
//                        .authorities(authorities)
//                        .build();
//
//                // 인증된 사용자 정보를 Spring Security의 SecurityContext에 설정
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        // 다음 필터 체인으로 요청 전달
//        filterChain.doFilter(request, response);
//    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("jwt_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}



