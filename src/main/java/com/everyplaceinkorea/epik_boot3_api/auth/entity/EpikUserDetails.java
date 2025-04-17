package com.everyplaceinkorea.epik_boot3_api.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpikUserDetails implements UserDetails, OAuth2User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImage;
    private Collection<? extends GrantedAuthority> authorities; // 사용자 권한 목록
    private Map<String, Object> attributes; // OAuth2 제공자로부터 받은 원본 속성

    @Override
    public String getName() {
        return this.username != null ? this.username : String.valueOf(this.id);
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
