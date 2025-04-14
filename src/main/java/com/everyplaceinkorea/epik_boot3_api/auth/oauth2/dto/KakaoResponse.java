package com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

  private final Map<String, Object> attributes;

  public KakaoResponse(Map<String, Object> attributes) {
    this.attributes = attributes;
    System.out.println("Kakao attributes: " + attributes);
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
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    return kakaoAccount.get("email").toString();
  }

  @Override
  public String getName() {
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    return properties.get("nickname").toString();
  }

  @Override
  public String getProfileImage() {
    // 프로필 이미지는 선택 동의 항목이므로, 동의하지 않았을 경우 처리
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    if(kakaoAccount == null) {
      return null;
    }

    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    if(profile == null) {
      return null;
    }

    // 프로필 이미지 동의 여부 확인

    return null;

  }

  // 기본 프로필 이미지 URL 반환
  private String getDefaultProfileImage() {
    return "/images/basic.png";
  }
}
