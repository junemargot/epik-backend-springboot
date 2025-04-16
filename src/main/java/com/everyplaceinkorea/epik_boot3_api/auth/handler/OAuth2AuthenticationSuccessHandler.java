package com.everyplaceinkorea.epik_boot3_api.auth.handler;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    log.info("OAuth2 로그인 성공");

    DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    // JWT 토큰 생성을 위한 사용자 정보 구성
    EpikUserDetails userDetails = EpikUserDetails.builder()
            .id((Long) attributes.get("id"))
            .username((String) attributes.get("username"))
            .email((String) attributes.get("email"))
            .nickname((String) attributes.get("nickname"))
            .profileImage((String) attributes.get("profileImage"))
            .authorities((Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))))
            .build();

    // JWT 토큰 생성
    String token = jwtUtil.generateToken(userDetails);

    // 쿠키에 JWT 토큰 저장
    ResponseCookie cookie = ResponseCookie.from("jwt_token", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(60 * 60 * 24) // 1일
            .build();

    // 프론트엔드 리다이렉트 구성 (토큰 쿼리 파라미터 추가)
    String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
            .queryParam("token", token)
            .build().toUriString();

    // 리다이렉트 실행
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}