package com.everyplaceinkorea.epik_boot3_api.anonymous.notice.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.notice.dto.NoticeResponseDto;

import java.util.List;

public interface NoticeService {

  // 전체 조회
  List<NoticeResponseDto> getAllNotices();

  NoticeResponseDto getAllNoticeWithPaging(int page, String keyword, String searchType);

  // 단건 조회

  NoticeResponseDto getNoticeById(Long id);



}
