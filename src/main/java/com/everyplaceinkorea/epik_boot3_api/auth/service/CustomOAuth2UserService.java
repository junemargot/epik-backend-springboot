package com.everyplaceinkorea.epik_boot3_api.auth.service;

import com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto.OAuthAttributes;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  /*
  * 사용자 정보를 처리하기 위한 클래스
  * */

  @Autowired
  private MemberRepository memberRepository;

  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    // OAuth2 서비스 ID (kakao, google, naver)
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
    String userNameAttributeName = userRequest.getClientRegistration()
                                              .getProviderDetails()
                                              .getUserInfoEndpoint()
                                              .getUserNameAttributeName();

    // OAuth2UserSerivce를 통해 가져온 OAuth2User의 attribute를 담을 클래스
    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    Member member = saveOrUpdate(attributes);

    return new DefaultOAuth2User(
            Collections.singleton(
                    new SimpleGrantedAuthority(member.getRole())),
                                               attributes.getAttributes(),
                                               attributes.getNameAttributeKey());
  }

  private Member saveOrUpdate(OAuthAttributes attributes) {
    Member member = memberRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(),
                                                 attributes.getPicture())).orElse(attributes.toEntity());

    return memberRepository.save(member);
  }


}
