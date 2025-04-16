package com.everyplaceinkorea.epik_boot3_api.entity.member;

public enum LoginType {
  KAKAO("카카오"),
  GOOGLE("구글"),
  NAVER("네이버"),
  ID("아이디"); // 이 값 추가 필요

  private final String description;

  LoginType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  // 문자열에서 LoginType으로 변환
  public static LoginType fromString(String provider) {
    if(provider == null) return ID;

    switch(provider.toLowerCase()) {
      case "kakao": return KAKAO;
      case "google": return GOOGLE;
      case "naver": return NAVER;
      default: return ID;
    }
  }
}
