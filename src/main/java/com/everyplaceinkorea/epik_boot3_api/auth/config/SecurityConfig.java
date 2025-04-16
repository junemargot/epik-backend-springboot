package com.everyplaceinkorea.epik_boot3_api.auth.config;

import com.everyplaceinkorea.epik_boot3_api.auth.filter.JwtAuthenticationFilter_v2;
import com.everyplaceinkorea.epik_boot3_api.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.everyplaceinkorea.epik_boot3_api.auth.handler.OAuth2LogoutSuccessHandler;
import com.everyplaceinkorea.epik_boot3_api.auth.service.CustomOAuth2UserService;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;
  private final JwtAuthenticationFilter_v2 jwtAuthenticationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    http
            // CSRF 보호 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // CORS 설정 적용
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            // 세션 관리 설정 (JWT 사용으로 STATELESS 모드)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 요청 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/auth/**", "/oauth/**", "/login/**", "/images/**").permitAll()
                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().permitAll()
            )

            // 인증방식 설정
            .formLogin(Customizer.withDefaults())
            // OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService)
                    ).successHandler(oAuth2AuthenticationSuccessHandler)
            )

            // 로그아웃 설정
            .logout(logout -> logout
                    .logoutUrl("/api/v1/auth/logout")
                    .logoutSuccessHandler(oAuth2LogoutSuccessHandler)
                    .deleteCookies("jwt_token") // 로그아웃 시 쿠키 삭제
                    .invalidateHttpSession(true)
            )

            // h2 콘솔 사용 설정
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions.sameOrigin()))

            // JWT 필터 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOriginPattern("*");
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.addAllowedHeader("*");
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}

//  @Bean
//  public SecurityFilterChain securityFilterChain(
//          HttpSecurity http,
//          CorsConfigurationSource corsConfigurationSource) throws Exception {
//
//    http.cors(cors -> cors.configurationSource(corsConfigurationSource))
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//            .requestMatchers("/post/**").authenticated()
//            .requestMatchers("/images/**").permitAll()
////            .requestMatchers("/login/**", "/oauth2/**").permitAll()
//            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //트라이
//            .anyRequest().permitAll())
//
//            // 인증방식 설정
//            .formLogin(Customizer.withDefaults())
//
//            // OAuth2 로그인 설정 추가
//            .oauth2Login(oauth2 -> oauth2
//                    .userInfoEndpoint(userInfo -> userInfo
//                            .userService(customOAuthUserService())
//                    ).successHandler(oAuth2AuthenticationSuccessHandler())
//            )
//
//            .sessionManagement(session-> session
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//            // H2 콘솔을 위한 설정
//            .headers(headers ->
//                    headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
//
//    // 아래 코드에서 @Bean으로 등록new JwtAuthenticationFilter_v2(jwtUtil)
//    // JwtAuthenticationFilter에서 정의한 내용(토큰 유효성 확인) 사용
//    http.addFilterBefore(new JwtAuthenticationFilter_v2(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }

//  @Bean
//  public UrlBasedCorsConfigurationSource corsConfigurationSource(){
//    CorsConfiguration config = new CorsConfiguration();
////        config.setAllowedOrigins(Arrays.asList(
////                "http://localhost"
////                ,"http://localhost:3000"
////                ,"http://localhost:3001"
////                ,"http://localhost:8080"
////                ,"http://localhost:8081"
////        ));
//    config.addAllowedOriginPattern("*"); // 모든 Origin 허용
//    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//    config.addAllowedHeader("*"); // 모든 헤더 허용
//    config.setAllowCredentials(true);
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", config);
//
//    return source;
//  }
//}
