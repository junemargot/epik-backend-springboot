package com.everyplaceinkorea.epik_boot3_api.auth.config;

import com.everyplaceinkorea.epik_boot3_api.auth.filter.JwtAuthenticationFilter_v2;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
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
public class SecurityConfig {

  private JwtUtil jwtUtil;

  public SecurityConfig(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
          HttpSecurity http,
          CorsConfigurationSource corsConfigurationSource) throws Exception {

    http
            // .configurationSource()는 cors 메소드의 설정 메소드, 즉 corsConfigurationSource 조건으로 설정하겠다
            .cors(cors->cors.configurationSource(corsConfigurationSource))
//                .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests
                    // 인터렙트 URL
                    -> authorizeRequests
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER")
                    .requestMatchers("/post/**").authenticated()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //트라이

                    .anyRequest().permitAll())
            // 인증방식 설정
            .formLogin(Customizer.withDefaults())

            .sessionManagement(session-> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // H2 콘솔을 위한 설정
            .headers(headers ->
                    headers.frameOptions(frameOptions ->
                            frameOptions.sameOrigin()
                    )
            );

    // 아래 코드에서 @Bean으로 등록new JwtAuthenticationFilter_v2(jwtUtil)
    // JwtAuthenticationFilter에서 정의한 내용(토큰 유효성 확인) 사용
    http.addFilterBefore(new JwtAuthenticationFilter_v2(jwtUtil), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public UrlBasedCorsConfigurationSource corsConfigurationSource(){
    CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList(
//                "http://localhost"
//                ,"http://localhost:3000"
//                ,"http://localhost:3001"
//                ,"http://localhost:8080"
//                ,"http://localhost:8081"
//        ));
    config.addAllowedOriginPattern("*"); // 모든 Origin 허용
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.addAllowedHeader("*"); // 모든 헤더 허용
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }

  // UserDetails -> UserDetailsService
    /*
    1. 메모리 유저
    2. JdBC 유저
    3. X.500 유저

     */

}
