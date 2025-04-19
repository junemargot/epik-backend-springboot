package com.everyplaceinkorea.epik_boot3_api.auth.handler;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    log.info("OAuth2 로그인 성공");
    EpikUserDetails userDetails = (EpikUserDetails) authentication.getPrincipal();

    // 최근 접속일 업데이트 로직 추가
    try {
      String username = userDetails.getUsername();
      String email = userDetails.getEmail();

      // 이메일로 회원 찾기 (소셜 로그인은 주로 이메일로 식별)
      Optional<Member> memberOptional = memberRepository.findByEmail(email);
      if (memberOptional.isPresent()) {
        Member member = memberOptional.get();
        // 최근 접속일 업데이트
        member.setLastAccess(LocalDateTime.now());
        memberRepository.save(member);
        log.info("소셜 로그인 사용자 {} 최근 접속일 업데이트 완료", email);
      } else {
        // 혹시 이메일로 찾지 못하면 username으로 시도
        memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isPresent()) {
          Member member = memberOptional.get();
          member.setLastAccess(LocalDateTime.now());
          memberRepository.save(member);
          log.info("소셜 로그인 사용자 {} 최근 접속일 업데이트 완료", username);
        } else {
          log.warn("소셜 로그인 사용자를 찾을 수 없음: {}", email);
        }
      }
    } catch (Exception e) {
      // 최근 접속일 업데이트 실패해도 로그인 프로세스는 계속 진행
      log.error("최근 접속일 업데이트 중 오류 발생", e);
    }

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