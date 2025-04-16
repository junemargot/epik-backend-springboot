package com.everyplaceinkorea.epik_boot3_api.auth.service;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto.GoogleResponse;
import com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto.KakaoResponse;
import com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto.OAuth2Response;
import com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto.OAuth2UserAttributes;
import com.everyplaceinkorea.epik_boot3_api.entity.member.LoginType;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  /*
  * 사용자 정보를 처리하기 위한 클래스
  * */

  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    // 소셜 로그인 제공자 ID (kakao, google, naver)
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
    String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

    // OAuth2 로그인 과정에서 얻은 사용자 속성을 OAuth2UserAttributes로 변환
    OAuth2UserAttributes attributes = OAuth2UserAttributes.of(
            registrationId,
            userNameAttributeName,
            oAuth2User.getAttributes()
    );

    // 사용자 정보 저장 또는 업데이트 - DB 저장
    Member member = saveOrUpdate(attributes, registrationId);

    // Spring Security 인증 객체 생성
    return EpikUserDetails.builder()
            .id(member.getId())
            .username(member.getUsername())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .profileImage(member.getProfileImg())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
            .attributes(oAuth2User.getAttributes())
            .build();
  }

  // 사용자 정보 저장 또는 업데이트
  private Member saveOrUpdate(OAuth2UserAttributes attributes, String registrationId) {
    LoginType loginType = "google".equals(registrationId) ? LoginType.GOOGLE : LoginType.KAKAO;
    String email = attributes.getEmail();

    Optional<Member> memberOptional = memberRepository.findByEmail(email);

    if(memberOptional.isPresent()) {
      Member member = memberOptional.get();

      if(attributes.getProfileImage() != null && !attributes.getProfileImage().equals("basic.png")) {
        member.setProfileImg(attributes.getProfileImage());
      }

      return memberRepository.save(member);
    } else {
      Member member = new Member();
      member.setUsername(email);
      member.setEmail(email);
      member.setNickname(attributes.getName());
      member.setProfileImg(attributes.getProfileImage() != null ? attributes.getProfileImage() : "basic.png");
      member.setJoinDate(LocalDate.now());
      member.setType((byte) 1);
      member.setRole("ROLE_MEMBER");
      member.setLoginType(loginType);

      return memberRepository.save(member);
    }
  }

  // 소셜 로그인 제공자에 맞는 OAuth2Response 구현체 반환
//  private OAuth2Response getOAuth2Response(String registrationId, Map<String, Object> attributes) {
//    if("kakao".equals(registrationId)) {
//      return new KakaoResponse(attributes);
//
//    } else if("google".equals(registrationId)) {
//      return new GoogleResponse(attributes);
//    }
//
//    throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인 제공자입니다.");
//  }

  // 회원 정보 저장 또는 업데이트
//  private Member saveOrUpdate(OAuth2Response oAuth2Response) {
//    Member member = memberRepository.findByEmail(oAuth2Response.getEmail())
//            .map(entity -> {
//              // 기존 회원이면 정보 업데이트
//              entity.updateProfile(oAuth2Response.getName(), oAuth2Response.getProfileImage());
//              return entity;
//            })
//            .orElse(createNewMember(oAuth2Response)); // 신규 회원이면 생성
//
//    return memberRepository.save(member);
//  }

  // 신규 회원 생성
//  private Member createNewMember(OAuth2Response oAuth2Response) {
//    return Member.builder()
//            .username(oAuth2Response.getEmail())
//            .email(oAuth2Response.getEmail())
//            .nickname(oAuth2Response.getName())
//            .profileImg(oAuth2Response.getProfileImage())
//            .role("ROLE_MEMBER")
//            .loginType(LoginType.fromString(oAuth2Response.getProvider()))
//            .type((byte) 1)
//            .joinDate(LocalDate.now())
//            .build();
//  }

  //
//  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//    OAuth2User oAuth2User = super.loadUser(userRequest);
//
//    // OAuth2 서비스 ID (kakao, google, naver)
//    String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//    // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
//    String userNameAttributeName = userRequest.getClientRegistration()
//                                              .getProviderDetails()
//                                              .getUserInfoEndpoint()
//                                              .getUserNameAttributeName();
//
//    // OAuth2UserSerivce를 통해 가져온 OAuth2User의 attribute를 담을 클래스
//    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//    Member member = saveOrUpdate(attributes);
//
//    return new DefaultOAuth2User(
//            Collections.singleton(
//                    new SimpleGrantedAuthority(member.getRole())),
//                                               attributes.getAttributes(),
//                                               attributes.getNameAttributeKey());
//  }
//
//  private Member saveOrUpdate(OAuthAttributes attributes) {
//    Member member = memberRepository.findByEmail(attributes.getEmail())
//            .map(entity -> entity.update(attributes.getName(),
//                                                 attributes.getPicture())).orElse(attributes.toEntity());
//
//    return memberRepository.save(member);
//  }


}
