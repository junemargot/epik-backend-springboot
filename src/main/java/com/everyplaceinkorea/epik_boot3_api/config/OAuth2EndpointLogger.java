package com.everyplaceinkorea.epik_boot3_api.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuth2EndpointLogger {

  @PostConstruct
  public void logEndpoint() {
    log.info("OAuth2 인증 경로: /oauth2/authorization/{registrationId}");
    log.info("OAuth2 콜백 경로: /login/oauth2/code/{registrationId}");
  }
}
