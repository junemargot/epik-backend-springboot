package com.everyplaceinkorea.epik_boot3_api.admin.auth.service;

import com.everyplaceinkorea.epik_boot3_api.admin.auth.dto.AdminUserDto;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminUserService {

  private final MemberRepository memberRepository;

  public AdminUserDto getCurrentAdminUser(Authentication authentication) throws AccessDeniedException {
    // 1. 인증 정보 검증
    if(authentication == null || !authentication.isAuthenticated()) {
      throw new AccessDeniedException("인증되지 않은 사용자입니다.");
    }

    // 2. 현재 로그인한 사용자 정보 추출
    String username = authentication.getName();

    // 3. DB에서 회원 조회
    Member admin = memberRepository.findByUsername("admin02")
            .orElseThrow(() -> new UsernameNotFoundException("관리자 회원을 찾을 수 없습니다."));

    // 4. 관리자 권한 확인
    if(!"ROLE_ADMIN".equals(admin.getRole())) {
      throw new AccessDeniedException("관리자 권한이 없습니다.");
    }

    // 5. DTO 변환
    return AdminUserDto.fromUser(admin);
  }
}
