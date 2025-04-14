package com.everyplaceinkorea.epik_boot3_api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoRequestDto {
//  private String accessToken;  // 카카오 인증 코드
//  private String redirectUrl;  // 리다이렉트 URI

  // 카카오 사용자 정보 (토큰 교환 후 사용)
  private Long id;             // 카카오 사용자 ID
  private String email;        // 이메일
  private String nickname;     // 닉네임
  private String profileImage; // 프로필 이미지
}
