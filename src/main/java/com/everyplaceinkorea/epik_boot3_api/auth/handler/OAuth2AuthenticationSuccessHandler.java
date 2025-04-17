package com.everyplaceinkorea.epik_boot3_api.auth.handler;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    log.info("OAuth2 로그인 성공");
    EpikUserDetails userDetails = (EpikUserDetails) authentication.getPrincipal();

    // JWT 토큰 생성
    String token = jwtUtil.generateToken(userDetails);

    // 쿠키에 JWT 토큰 저장
    ResponseCookie cookie = ResponseCookie.from("jwt_token", token)
            .httpOnly(true)
            .secure(false) // 개발 환경에선 false
            .path("/")
            .maxAge(60 * 60 * 24) // 1일
            .sameSite("Lax") // sameSite 속성 추가
            .domain("localhost") // 개발 환경의 도메인
            .build();

    log.info("JWT 토큰 쿠키 설정: {}", cookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    // 프론트엔드 리다이렉트 구성 (토큰 쿼리 파라미터 추가)
    String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/redirect")
            .queryParam("login_success", "true")
            .queryParam("token", token)
            .build().toUriString();

    // 리다이렉트 실행
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}