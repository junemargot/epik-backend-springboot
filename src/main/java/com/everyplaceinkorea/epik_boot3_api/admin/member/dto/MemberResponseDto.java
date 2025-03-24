package com.everyplaceinkorea.epik_boot3_api.admin.member.dto;

import com.everyplaceinkorea.epik_boot3_api.admin.member.LoginType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor // 기본 생성자만 사용
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
public class MemberResponseDto {
  // 응답받을 DTO
  private Long id;                  // 회원번호

  @Enumerated(EnumType.STRING)      // DB에 저장될 때 enum 값이 문자열로 저장되도록 지정
  private LoginType loginType;      // 가입유형(로그인타입)

  private String nickname;          // 닉네임
  private String email;             // 이메일
  private String username;          // 아이디 -> username으로 컬럼명 변경
  private LocalDate joinDate;       // 가입일
  private LocalDateTime lastAccess; // 마지막접속
  private Byte type;                // 회원 분류
  private String profileImg;        // 프로필 사진
  private Integer reportedCount;    // 누적 신고수(피드, 피드댓글)
  private Integer writtenFeedCount; // 피드 작성수



//
//  // 가입유형을 문자열로 변환하여 반환
//  public LoginType getLoginType() {
//    return loginType;
//  }
//
////  // loginType을 별도로 설정하기 위함. (ModelMapper에서 자동 매핑되지 않도록 추가)
//  public void setLoginType(LoginType loginType) {
//    this.loginType = loginType;
//  }
}
