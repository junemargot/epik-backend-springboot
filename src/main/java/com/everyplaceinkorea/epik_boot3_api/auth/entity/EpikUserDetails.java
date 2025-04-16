package com.everyplaceinkorea.epik_boot3_api.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpikUserDetails implements UserDetails, OAuth2User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImage;
    private Collection<? extends GrantedAuthority> authorities; // *
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    // 빌더 패턴에서 attributes가 null일 경우 빈 맵으로 초기화하는 정적 메서드
//    public static EpikUserDetails builder() {
//        return new CustomEpikUserDetailsBuilder();
//    }

//    private static class CustomEpikUserDetailsBuilder extends EpikUserDetails {
//
//        public EpikUserDetails build() {
//            EpikUserDetails userDetails = super.build();
//            if (userDetails.getAttributes() == null) {
//                userDetails.setAttributes(Map.of());
//            }
//            return userDetails;
//        }
//    }
}
