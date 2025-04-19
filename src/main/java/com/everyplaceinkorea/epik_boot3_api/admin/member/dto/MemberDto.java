package com.everyplaceinkorea.epik_boot3_api.admin.member.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.member.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
  private Long id;
  private String profileImg;
  private String username; // id
  private String nickname;
  private LocalDate joinDate;
  private LocalDateTime lastAccess;
  private String email;
  private LoginType loginType;
}