package com.everyplaceinkorea.epik_boot3_api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUserInfoDto {

  private Long id;
  private String email;
  private String nickname;
  private String profileImage;

  // 카카오 응답에서 필요한 정보를 추출하는 정적 메서드
  public static KakaoUserInfoDto fromAttributes(Map<String, Object> attributes) {
    Long id = (Long) attributes.get("id");
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

    String nickname = properties != null ? (String) properties.get("nickname") : null;
    String profileImage = properties != null ? (String) properties.get("profile_image") : null;
    String email = kakaoAccount != null && (Boolean) kakaoAccount.getOrDefault("has_email", false) ?
            (String) kakaoAccount.get("email") : null;

    return KakaoUserInfoDto.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .profileImage(profileImage)
            .build();
  }
}
