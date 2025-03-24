package com.everyplaceinkorea.epik_boot3_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//
//    registry.addMapping("/api/**") // 모든 경로에 대해 CORS 설정
//            .allowedOrigins("http://localhost:3000","http://localhost:3002") // Nuxt.js 애플리케이션 URL
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
//            .allowedHeaders("*") // 모든 헤더 사용
//            .allowCredentials(true); // 필요에 따라 설정
//  }


  /*
  * 기본 리소스 위치에 새로운 경로 추가해주기
  */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**") // 해당 경로 요청이 오면
            .addResourceLocations("file:uploads/"); // 지정한 경로에서 제공

  }
}
