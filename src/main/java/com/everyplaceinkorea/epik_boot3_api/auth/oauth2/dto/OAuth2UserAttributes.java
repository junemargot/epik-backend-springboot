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
public class OAuth2UserAttributes {

  // OAuth2User에서 받아온 원본 속성값들을 저장
  private Map<String, Object> attributes;

  // OAuth2 로그인 진행 시 키가 되는 필드 값 (PK와 같은 역할)
  private String nameAttributeKey;
  private String name;
  private String email;
  private String profileImage;

  @Builder
  public OAuth2UserAttributes(Map<String, Object> attributes,
                              String nameAttributeKey,
                              String name,
                              String email,
                              String profileImage) {

    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.profileImage = profileImage;
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
  public static OAuth2UserAttributes of(String registrationId,
                                        String userNameAttributeName,
                                        Map<String, Object> attributes) {

    if("google".equals(registrationId)) {
      return ofGoogle(userNameAttributeName, attributes);
    } else if("kakao".equals(registrationId)) {
      return ofKakao(userNameAttributeName, attributes);
    } else if("naver".equals(registrationId)) {
      return ofNaver(userNameAttributeName, attributes);
    }

    return OAuth2UserAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .profileImage((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  private static OAuth2UserAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    // kakao는 kakao_account에 유저 정보가 있다.
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

    // kakao_account 안에 또 profile이라는 JSON 객체가 있다. (nickname, profile_image)
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuth2UserAttributes.builder()
            .name((String) kakaoProfile.get("nickname"))
            .email((String) kakaoProfile.get("email"))
            .profileImage((String) kakaoProfile.get("profile_image_url"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  private static OAuth2UserAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuth2UserAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .profileImage((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  private static OAuth2UserAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

    // 네이버는 response 객체 안에 사용자 정보를 담아서 제공한다.
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuth2UserAttributes.builder()
            .name((String) response.get("name"))
            .email((String) response.get("email"))
            .profileImage((String) response.get("profile_image"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  // Member 엔티티로 변환
  public Member toMember() {

    Member member = new Member();
    member.setEmail(email);
    member.setUsername(email);
    member.setNickname(name);
    member.setProfileImg(profileImage);
    member.setJoinDate(LocalDate.now());
    member.setType((byte) 1);
    member.setRole("ROLE_MEMBER");

    if(attributes.containsKey("sub")) {
      member.setLoginType(LoginType.GOOGLE);
    } else if(attributes.containsKey("id") && attributes.get("id").toString().length() > 10) {
      member.setLoginType(LoginType.KAKAO);
    } else {
      member.setLoginType(LoginType.NAVER);
    }

    return member;
  }
}
