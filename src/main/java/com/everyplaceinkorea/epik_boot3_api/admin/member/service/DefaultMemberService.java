package com.everyplaceinkorea.epik_boot3_api.admin.member.service;

import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.member.dto.MemberResponseDto;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;

  // 모든 회원 조회
//  @Override
//  public List<MemberResponseDto> getAllMembers() {
//    List<Member> members = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//
//    return members.stream()
//            .map(member -> {
//              // model mapping을 통해 Member -> MemberResponseDto로 변환
//              MemberResponseDto memberResponseDto = modelMapper.map(member, MemberResponseDto.class);
//
//              // loginType을 LoginType enum으로 변환
//              memberResponseDto.setLoginType(LoginType.fromCode(member.getLoginType()));
//              return memberResponseDto;
//            }).collect(Collectors.toList());
//  }
  // 모든 회원 조회
  // 모든 회원 조회
  @Override
  public List<MemberResponseDto> getAllMembers() {
    List<Member> members = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

    return members.stream()
            .map(member -> {
              // model mapping을 통해 Member -> MemberResponseDto로 변환
              MemberResponseDto memberResponseDto = modelMapper.map(member, MemberResponseDto.class);

              // loginType은 이미 Enum으로 저장되어 있기 때문에 별도 변환없이 그대로 설정됨
              // JPA가 자동으로 Enum을 처리하기 때문에 따로 변환 작업이 필요 없다.
              return memberResponseDto;
            })
            .collect(Collectors.toList());
  }

  // 멤버 조회(페이징)
  @Override
  public MemberListDto getAllMembersWithPaging(int page, String keyword, String searchType) {
    // 기본 페이지 번호 설정 - 클라이언트는 1-based 페이지 인덱스를 사용하므로 Spring JPA의 0-based 인덱스에 맞게 변환
    int pageNumber = page - 1;

    // 한 페이지에 표시할 데이터 개수
    int pageSize = 15;

    // 게시물 ID 기준 내림차순 정렬(최신 게시물이 먼저 나오도록 설정)
    Sort sort = Sort.by("id").descending();

    // 페이지 요청 객체 생성
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    // 검색 조건 데이터 가져오기
    Page<Member> memberPage = memberRepository.searchMember(keyword, searchType, pageable);
    memberPage.forEach(member -> System.out.println("MEMBER JOIN DATE: " + member.getJoinDate()));

    // 페이징 결과
    long totalCount = memberPage.getTotalElements(); // 총 게시물 수
    int totalPages = memberPage.getTotalPages(); // 총 페이지 수
    boolean hasPrevPage = memberPage.hasPrevious();
    boolean hasNextPage = memberPage.hasNext();

    List<MemberDto> memberDtos = memberPage.getContent()
            .stream()
            .map(member -> {
              MemberDto memberDto = modelMapper.map(member, MemberDto.class);
//              MemberDto memberDto = new MemberDto();
//              memberDto.setJoinDate(member.getJoinDate());
              return memberDto;
            }).toList();

    // 페이지 목록 생성
    int currentPage = memberPage.getNumber() + 1;
    List<Long> pages = LongStream.rangeClosed(
            Math.max(1, currentPage - 2),
            Math.min(totalPages, currentPage + 2)
    ).boxed().collect(Collectors.toList());

    return MemberListDto.builder()
            .memberList(memberDtos)
            .totalCount(totalCount)
            .totalPages(totalPages)
            .hasPrev(hasPrevPage)
            .hasNext(hasNextPage)
            .pages(pages)
            .build();
  }

  // 특정 회원 조회
  @Override
  public MemberResponseDto getMemberById(Long id) {
    Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MEMBER NOT FOUND"));

    // model mapping을 통해 Member -> MemberResponseDto로 변환
    MemberResponseDto memberResponseDto = modelMapper.map(member, MemberResponseDto.class);

    // loginType을 enum으로 설정(JPA에서 자동으로 Enum 타입으로 처리되므로 그대로 사용한다.)
    memberResponseDto.setLoginType(member.getLoginType());

    return memberResponseDto;
//    return modelMapper.map(member, MemberResponseDto.class);

  }

  // 회원 수정 -> 계정 상태에 대한 업데이트 처리
  @Override
  public MemberResponseDto updateMember(MemberRequestDto memberRequestDto) {
    return null;
  }

  // 회원 삭제
  @Override
  public void deleteMember(Long id) {
    if(!memberRepository.existsById(id)) {
      throw new RuntimeException("MEMBER NOT FOUND");
    }

    memberRepository.deleteById(id);
  }
}
