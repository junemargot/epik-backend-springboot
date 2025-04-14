package com.everyplaceinkorea.epik_boot3_api.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    // 여기에서 JWT 토큰 생성 등의 추가 작업을 수행할 수 있다.
    // FrontEnd 리다이렉트 또는 토큰을 전달하는 로직 구현
    getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/oauth2/redirect");

  }
}
