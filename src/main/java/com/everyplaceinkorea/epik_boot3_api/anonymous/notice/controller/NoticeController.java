package com.everyplaceinkorea.epik_boot3_api.anonymous.notice.controller;

import com.everyplaceinkorea.epik_boot3_api.anonymous.notice.dto.NoticeResponseDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeController {

  private final NoticeService noticeService;

  @GetMapping()
  public ResponseEntity<NoticeResponseDto> getAllNotices(
          @RequestParam(name = "p", defaultValue = "1") Integer page,
          @RequestParam(name = "k", required = false) String keyword,
          @RequestParam(name = "s", required = false) String searchType) {

    NoticeResponseDto noticeResponseDto = noticeService.getAllNoticeWithPaging(page, keyword, searchType);

    return ResponseEntity.ok(noticeResponseDto);

  }

  /*
   * 모든 공지사항 목록 가져오기
   * @return 공지사항 리스트
   */
//  @GetMapping
//  public ResponseEntity<List<NoticeResponseDto>> getAllNotices() {
//
//    List<NoticeResponseDto> notices = noticeService.getAllNotices();
//
//    return ResponseEntity.ok(notices);
//  }

  /*
   * ID를 기준으로 특정 공지사항 가져오기
   * @return 공지사항 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long id) {

    NoticeResponseDto notice = noticeService.getNoticeById(id);

    return ResponseEntity.ok(notice);
  }
}
