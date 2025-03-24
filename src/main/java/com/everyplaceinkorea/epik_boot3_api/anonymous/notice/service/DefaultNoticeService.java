package com.everyplaceinkorea.epik_boot3_api.anonymous.notice.service;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto.ConcertListDto;
import com.everyplaceinkorea.epik_boot3_api.repository.NoticeRepository;
import com.everyplaceinkorea.epik_boot3_api.anonymous.notice.dto.NoticeResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.Notice;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class DefaultNoticeService implements NoticeService {

  private final NoticeRepository noticeRepository;
  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<NoticeResponseDto> getAllNotices() {
    // 목록 내림차순으로 가져오기
    List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

    // NoticeResponseDto 리스트로 변환
    List<NoticeResponseDto> responseDtos = notices.stream()
            .map(notice -> {
              NoticeResponseDto responseDto = modelMapper.map(notice, NoticeResponseDto.class);
              responseDto.setWriter(notice.getMember().getNickname()); // 작성자 닉네임 설정
              return responseDto;
            })
            .collect(Collectors.toList());

    return responseDtos;
  }

  @Override
  public NoticeResponseDto getAllNoticeWithPaging(int page, String keyword, String searchType) {
    Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "id"));

    Page<Notice> noticePage = noticeRepository.searchNotice(pageable, keyword, searchType);

    long totalCount = noticePage.getTotalElements(); // 총 게시물 수
    int totalPages = noticePage.getTotalPages(); // 총 페이지 수
    boolean hasPrevPage = noticePage.hasPrevious();
    boolean hasNextPage = noticePage.hasNext();

    // 페이지네이션을 위한 페이지 번호 목록 생성
    int currentPage = noticePage.getNumber() + 1;
    List<Long> pages = LongStream.rangeClosed(
            Math.max(1, currentPage - 2),
            Math.min(totalPages, currentPage + 2)
    ).boxed().collect(Collectors.toList());

    List<NoticeResponseDto> noticeDtos = noticePage.getContent()
            .stream()
            .map(notice -> {
              NoticeResponseDto responseDto = modelMapper.map(notice, NoticeResponseDto.class);
              responseDto.setWriter(notice.getMember().getNickname());
              return responseDto;
            }).collect(Collectors.toList());



    // 응답 반환
    return NoticeResponseDto.builder()
            .noticeList(noticeDtos)
            .totalPages(totalPages)
            .hasPrev(hasPrevPage)
            .hasNext(hasNextPage)
            .pages(pages)
            .build();


  }

  @Transactional
  @Override
  public NoticeResponseDto getNoticeById(Long id) {
    Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NOTICE NOT FOUND"));

    // anonymous와 member쪽의 공지사항에서 값을 가져와야함.
    notice.setViewCount(notice.getViewCount() + 1); // 현재 조회수에 1을 더하고
    noticeRepository.save(notice); // <- 호출하여 변경된 조회수를 DB에 반영한다.

    NoticeResponseDto responseDto = modelMapper.map(notice, NoticeResponseDto.class);
    responseDto.setWriter(notice.getMember().getNickname());
    return responseDto;
  }
}
