package com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.member.LoginType;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

/**
* OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
* 1. 각 소셜로그인 제공자에서 받아오는 데이터 형식이 다르므로 이를 표준화
* 2. 소셜로그인 제공자별로 데이터를 적절히 매핑하여 Member 엔티티로 변환
* 3. 여러 소셜로그인 제공자의 정보를 일관된 방식으로 처리할 수 있게 한다.
* */
@Getter
public class OAuthAttributes {

  // OAuth2User에서 받아온 원본 속성값들을 저장
  private Map<String, Object> attributes;

  // OAuth2 로그인 진행 시 키가 되는 필드 값 (PK와 같은 역할)
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes,
                         String nameAttributeKey,
                         String name,
                         String email,
                         String picture) {

    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  /**
   * OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 함
   * 소셜로그인 제공자에 따라 적절한 매핑 메서드를 호출
   *
   * @param registrationId 소셜 로그인 제공자 ID (kakao, google 등)
   * @param userNameAttributeName OAuth2 로그인 진행 시 키가 되는 필드 값
   * @param attributes OAuth2User에서 받아온 속성값들
   * @return 변환된 OAuthAttributes 객체
  * */
  public static OAuthAttributes of(String registrationId,
                                   String userNameAttributeName,
                                   Map<String, Object> attributes) {

    // 카카오 로그인인 경우
    if("kakao".equals(registrationId)) {
      return ofKakao("id", attributes);
    }

    // 네이버 로그인인 경우
//    if("naver".equals(registrationId)) {
//      return ofNaver("id", attributes);
//    }

    // 구글 로그인인 경우 (기본값)
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName,
                                         Map<String, Object> attributes) {
    // kakao는 kakao_account에 유저 정보가 있다.
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

    // kakao_account 안에 또 profile이라는 JSON 객체가 있다. (nickname, profile_image)
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuthAttributes.builder()
            .name((String) kakaoProfile.get("nickname"))
            .email((String) kakaoProfile.get("email"))
            .picture((String) kakaoProfile.get("profile_image_url"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  public Member toEntity() {
    return Member.builder()
            .username(email)
            .nickname(name)
            .email(email)
            .profileImg(picture)
            .role("ROLE_USER")
            .loginType(LoginType.KAKAO)
            .type((byte) 1)
            .joinDate(LocalDate.now())
            .build();
  }
}
