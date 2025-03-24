package com.everyplaceinkorea.epik_boot3_api.admin.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {
  ID("아이디"),      // 0
  NAVER("네이버"),   // 1
  KAKAO("카카오"),   // 2
  GOOGLE("구글");   // 3

  private final String loginType;

  // 문자열 값을 반환하는 getter
  public String getLoginType() {
    return loginType;
  }
//
//  // 문자열 값으로 LoginType enum을 반환하는 메서드
//  public static LoginType fromString(String code) {
//    for (LoginType loginType : LoginType.values()) {
//      if (loginType.getLoginType().equals(code)) {
//        return loginType;
//      }
//    }
//    return null; // 매핑되는 값이 없을 경우
//  }
}