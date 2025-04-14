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
}


//ID("아이디"),      // 0
//NAVER("네이버"),   // 1
//KAKAO("카카오"),   // 2
//GOOGLE("구글");   // 3