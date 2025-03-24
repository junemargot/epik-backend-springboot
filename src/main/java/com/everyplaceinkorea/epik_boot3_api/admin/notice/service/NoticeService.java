package com.everyplaceinkorea.epik_boot3_api.admin.notice.service;

import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeSearchDto;

import java.util.List;

public interface NoticeService {

  // 전체 조회(페이징, 검색)
  NoticeListDto getAllNotices(Integer page, String keyword, String searchType);

  NoticeListDto getAllNotices(NoticeSearchDto searchDto);
  // 전체 조회
//  NoticeListDto getAllNotices();

  // 상세 조회
  NoticeResponseDto getNoticeById(Long id);

  // 등록
  NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDTO);

  // 수정
  NoticeResponseDto updateNotice(Long id, NoticeRequestDto noticeRequestDTO);

  // 삭제
  void deleteNotice(Long id);

}
