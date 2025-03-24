package com.everyplaceinkorea.epik_boot3_api.auth.filter;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Getter
//    @Value("rland.jwt.secret")
//    private String secret;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        // 인증 토큰을 가지고 있다면
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // 토큰의 유효성을 검사하고
            if(jwtUtil.validateToken(token)){
                // 사용자 정보를 꺼내서
                String username = jwtUtil.extractUsername(token);
                List<String> roles = jwtUtil.extractRoles(token);

                // 인증 정보를 담는다.
                if (username != null && username != "") {
                    // 사용자 정보 + role 데이터까지 로드 한 거임.
                    List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
                    for (String role : roles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }

                    UserDetails userDetails = EpikUserDetails.builder()
//                                                .id(member.getId())
                                                .username(username)
//                                                .password(member.getPwd())
//                                                .email(member.getEmail())
                                                .authorities(authorities)
                                                .build();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 위쪽은 사전처리(인증 정보를 줄테니 다음 필터가 권한을 체크해 보세요)
        filterChain.doFilter(request, response); // -> 권한 필터에게 전달
        // 다음은 사후처리

    }
}
