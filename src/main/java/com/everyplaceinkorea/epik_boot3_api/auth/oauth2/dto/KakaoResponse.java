package com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto;

import java.util.Map;

/**
 * 카카오 OAuth2 응답을 처리하는 구현체
 * 카카오 API의 응답 형식에 맞게 데이터를 추출하여 표준화된 인터페이스로 제공
 */
public class KakaoResponse implements OAuth2Response {

  private final Map<String, Object> attributes;

  public KakaoResponse(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String getProviderId() {
    return attributes.get("id").toString();
  }

  @Override
  public String getEmail() {
    // 카카오 응답 구조: kakao_account > email
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    if(kakaoAccount == null || kakaoAccount.get("email") == null) {
      return "";
    }
    return kakaoAccount.get("email").toString();
  }

  @Override
  public String getName() {
    // 카카오 응답 구조: kakao_account > profile > nickname
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("properties");
    if(kakaoAccount == null) return "";

    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    if(profile == null || profile.get("nickname") == null) {
      return "";
    }

    return profile.get("nickname").toString();
  }

  @Override
  public String getProfileImage() {
    // 프로필 이미지는 선택 동의 항목이므로, 동의하지 않았을 경우 처리
    // 카카오 응답 구조: kakao_account > profile > nickname
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    if(kakaoAccount == null) {
      return getDefaultProfileImage();
    }

    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    if(profile == null || profile.get("profile_image_url") == null) {
      return getDefaultProfileImage();
    }

    return profile.get("profile_image_url").toString();
  }

  // 기본 프로필 이미지 URL 반환
  private String getDefaultProfileImage() {
    return "/images/basic.png";
  }
}
