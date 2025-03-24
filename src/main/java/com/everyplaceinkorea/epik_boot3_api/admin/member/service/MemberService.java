package com.everyplaceinkorea.epik_boot3_api.admin.member.service;

import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberResponseDto;

import java.util.List;
import java.util.Map;

public interface MemberService {

  // 멤버 전체 조회
  List<MemberResponseDto> getAllMembers();

  MemberListDto getAllMembersWithPaging(int page, String keyword, String searchType);
  // 멤버 특정 조회
  MemberResponseDto getMemberById(Long id);
  // 멤버 상태 수정
  MemberResponseDto updateMember(MemberRequestDto memberRequestDto);
  // 멤버 삭제
  void deleteMember(Long id);
}
