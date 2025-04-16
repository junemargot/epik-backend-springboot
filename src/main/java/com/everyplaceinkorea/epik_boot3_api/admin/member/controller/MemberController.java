package com.everyplaceinkorea.epik_boot3_api.admin.member.controller;

import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/member")
public class MemberController {

  private final MemberService memberService;

  // 회원 목록 조회
//  @GetMapping
//  public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
//    List<MemberResponseDto> members = memberService.getAllMembers();
//
//    return ResponseEntity.ok(members);
//  }

  @GetMapping
  public ResponseEntity<MemberListDto> getAllMembersWithPaging(
          @RequestParam(name = "p", defaultValue = "1") Integer page,
          @RequestParam(name = "k", required = false) String keyword,
          @RequestParam(name = "s", required = false) String searchWord) {

    MemberListDto members = memberService.getAllMembersWithPaging(page, keyword, searchWord);
    System.out.println("MEMBER LIST: " + members);
    return ResponseEntity.ok().body(members);

  }

  // 회원 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long id) {
    try {
      MemberResponseDto member = memberService.getMemberById(id);

      return ResponseEntity.ok(member);

    }catch(RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
    try {
      memberService.deleteMember(id);
      return ResponseEntity.noContent().build();
    } catch(RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
