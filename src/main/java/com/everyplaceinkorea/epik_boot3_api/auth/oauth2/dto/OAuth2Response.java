package com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto;

/*
* 소셜로그인 제공자로부터 받은 사용자 정보를 표준화하는 인터페이스
* 각 소셜 로그인 제공자(카카오, 네이버, 구글 등)는 이 인터페이스를 구현하여
* 자신만의 응답 형식을 표준화된 형태로 변환한다.
* */
public interface OAuth2Response {

  // 소셜로그인 제공자 이름 반환 (예: kakao, naver, google, ..)
  String getProvider();

  // 소셜로그인 제공자가 발급해한 고유 ID(번호) 반환
  String getProviderId();

  // 사용자 이메일 반환 - (동의 항목에 포함된 경우)
  String getEmail();

  // 사용자 이름(닉네임) 반환
  String getName();

  // 사용자 프로필 이미지 URL 반환(선택 동의 항목) - 동의하지 않은 경우 기본 이미지 URL 반환 가능
  String getProfileImage();
}
