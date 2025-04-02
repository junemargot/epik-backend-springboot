package com.everyplaceinkorea.epik_boot3_api.admin.auth.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserDto {

  private String nickname;
  private String email;
  private String role;
  private String profileImg;

  public static AdminUserDto fromUser(Member member) {
    return AdminUserDto.builder()
            .nickname(member.getNickname())
            .email(member.getEmail())
            .role(member.getRole())
            .profileImg(member.getProfileImg())
            .build();
  }
}
