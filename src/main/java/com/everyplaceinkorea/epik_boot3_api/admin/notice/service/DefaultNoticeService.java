package com.everyplaceinkorea.epik_boot3_api.admin.notice.service;

import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeSearchDto;
import com.everyplaceinkorea.epik_boot3_api.repository.NoticeRepository;
import com.everyplaceinkorea.epik_boot3_api.entity.Notice;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultNoticeService implements NoticeService{

  private final NoticeRepository noticeRepository;
  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;

  /* -- 페이징, 검색 목록 리스트
    page 요청 페이지 번호 (1부터 시작)
    keyword 검색 키워드
    searchType 검색 유형(ALL, TITLE, CONTENT, WRITER)
    페이징 및 검색 결과가 포함된 NoticeResponseDto
  */
  @Override
  public NoticeListDto getAllNotices(Integer page, String keyword, String searchType) {

    // 게시물 ID 기준 내림차순 정렬
    Sort sort = Sort.by("id").descending();

    // 페이지 요청 객체 생성(페이지 번호는 0부터 시작하므로 1을 빼줌, 한 목록에 1~5페이지)
    Pageable pageable = PageRequest.of(page - 1, 15, sort);

    // 검색 조건을 적용해 조회
    Page<Notice> noticePage = noticeRepository.searchNotice(pageable, keyword, searchType);

    // 목록 내용 Entity -> Dto 변환
    List<NoticeResponseDto> noticeResponseDtos = noticePage
                                .getContent()
                                .stream()
                                .map(notice -> {
            NoticeResponseDto noticeResponseDto = modelMapper.map(notice, NoticeResponseDto.class);
            noticeResponseDto.setWriter(notice.getMember().getNickname());

            return noticeResponseDto;
    }).toList();

    noticePage.getContent().stream().forEach(System.out::println);

    // 페이징 정보
    long totalCount = noticePage.getTotalElements(); // 전체 데이터 개수 반환
    int totalPages = noticePage.getTotalPages();    // 전체 페이지 수 반환
    boolean hasNextPage = noticePage.hasNext();      // 다음 페이지가 있는지 여부 반환
    boolean hasPrevPage = noticePage.hasPrevious();  // 이전 페이지가 있는지 여부 반환

    // page 번호가 null인 경우 기본값 1로 설정
    page = (page == null || page <= 0) ? 1 : page;

    // 현재 페이지 기준으로 표시할 페이지 번호 목록 계산
    int offset = (page - 1) % 5;
    int startPageNum = page - offset;
//    List<Long> pages = LongStream.range(startPageNum, Math.min(startPageNum + 5, totalPages + 1)).boxed().toList();
    List<Long> pages = new ArrayList<>();
    for(long i = startPageNum; i < Math.min(startPageNum + 5, totalPages + 1); i ++) {
      pages.add(i);
    }

    // 응답 dto 생성 및 반환
    return NoticeListDto.builder()
            .noticeList(noticeResponseDtos) // 공지사항 목록
            .totalCount(totalCount)         // 전체 공지사항
            .totalPages(totalPages)         // 전체 페이지
            .hasNext(hasNextPage)           // 다음 페이지
            .hasPrev(hasPrevPage)           // 이전 페이지
            .pages(pages)                   // 표시할 페이지
            .build();
  }

  // dto로 담아서 쓰는 방법
  @Override
  public NoticeListDto getAllNotices(NoticeSearchDto searchDto) {
    return null;
  }

//  @Override
//  public NoticeListDto getAllNotices() {
//    // 목록 내림차순으로 가져오기
//    List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//
//    // 총 게시물 수 구하기
//    int totalCount = (int) noticeRepository.count();
//
//    // 목록 반환
//    notices.stream()
//                  .map(notice -> {
//                    NoticeListDto noticeListDto = new NoticeListDto();
//                    NoticeResponseDto noticeResponseDto = modelMapper.map(notice, NoticeResponseDto.class);
//                    noticeListDto.setTotalCount(totalCount); // totalCount 반환
//                      noticeResponseDto.setWriter(notice.getMember().getNickname());
//
//                    noticeListDto.setTotalCount();
//                    return noticeResponseDto;
//                  }).collect(Collectors.toList());
//
//    return null;
//  }

  @Transactional // 조회와 업데이트를 하나의 트랜잭션으로 처리해 데이터 일관성 보장
  @Override
  public NoticeResponseDto getNoticeById(Long id) {
    Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NOTICE NOT FOUND"));

    // 조회수 증가
    // * 수정필요 * 이 기능은 관리자페이지의 조회수가 아니라, anonymous와 member쪽의 공지사항에서 값을 가져와야함.
    notice.setViewCount(notice.getViewCount() + 1); // 현재 조회수에 1을 더하고
    noticeRepository.save(notice); // <- 호출하여 변경된 조회수를 DB에 반영한다.

    NoticeResponseDto responseDto = modelMapper.map(notice, NoticeResponseDto.class);
    responseDto.setWriter(notice.getMember().getNickname());
    return responseDto;
  }

  @Override
  public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDTO) {
//    if(noticeRequestDTO.getWriter() == null) {
//      throw new IllegalArgumentException("WRITER ID MUST NOT BE NULL");
//    }

    noticeRequestDTO.setWriter(1L);

    Member member = memberRepository.findById(noticeRequestDTO.getWriter()).orElseThrow(()
            -> new EntityNotFoundException("MEMBER NOT FOUND WITH ID: " + noticeRequestDTO.getWriter()));

    // 요청 dto -> entity
    Notice notice = modelMapper.map(noticeRequestDTO, Notice.class);

    notice.setMember(member);
    notice = noticeRepository.save(notice);

    // 응답 dto
    return modelMapper.map(notice, NoticeResponseDto.class);
  }

  @Override
  public NoticeResponseDto updateNotice(Long id, NoticeRequestDto noticeRequestDTO) {
    Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NOTICE NOT FOUND"));

    // 엔티티 수정
    notice.setTitle(noticeRequestDTO.getTitle());
    notice.setContent(noticeRequestDTO.getContent());
    notice = noticeRepository.save(notice);

    NoticeResponseDto responseDto = modelMapper.map(notice, NoticeResponseDto.class);
    responseDto.setWriter(notice.getMember().getNickname());

    return responseDto;
//    return modelMapper.map(notice, NoticeResponseDto.class);
  }

  @Override
  public void deleteNotice(Long id) {
    if(!noticeRepository.existsById(id)) {
      throw new RuntimeException("NOTICE NOT FOUND");
    }

    noticeRepository.deleteById(id);
  }
}
