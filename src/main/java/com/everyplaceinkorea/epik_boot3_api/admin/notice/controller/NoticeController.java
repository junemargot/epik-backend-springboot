package com.everyplaceinkorea.epik_boot3_api.admin.notice.controller;

import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.dto.NoticeSearchDto;
import com.everyplaceinkorea.epik_boot3_api.admin.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/notice")
public class NoticeController {

  private final NoticeService noticeService;

  // 공지사항 목록 조회(페이징, 검색)
  @GetMapping
  public ResponseEntity<NoticeListDto> getAllNotices(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "k", required = false) String keyword,
                                                     @RequestParam(name = "s", required = false) String searchType) {
//    NoticeListDto notices = noticeService.getAllNotices();

    return ResponseEntity.ok()
            .body(noticeService.getAllNotices(page, keyword, searchType));
  }

  // 공지사항 목록 조회(검색)
//  @GetMapping
//  public ResponseEntity<NoticeListDto> getAllNotices(@ModelAttribute NoticeSearchDto searchDto) {
//    if(searchDto.getPage() == null || searchDto.getPage() < 1) {
//      searchDto.setPage(1);
//    }
//

//    NoticeListDto notices = noticeService.getAllNotices(searchDto);
//
//    return ResponseEntity.ok(notices);
////    return ResponseEntity.ok(noticeService.getAllNotices(searchDto));
//  }

  // 공지사항 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long id) {
    try {
      NoticeResponseDto notice = noticeService.getNoticeById(id);
      return ResponseEntity.ok(notice); // 정상 조회 시 200 OK 반환

    } catch(RuntimeException e) {
      return ResponseEntity.notFound().build(); // 404 NOT FOUND 반환
    }

  }

  // 공지사항 작성
  @PostMapping
  public ResponseEntity<NoticeResponseDto> createNotice(@RequestBody NoticeRequestDto noticeRequestDTO) {
    NoticeResponseDto createdNotice = noticeService.createNotice(noticeRequestDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
  }

  // 공지사항 수정
  @PatchMapping("/{id}")
  public ResponseEntity<NoticeResponseDto> updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto noticeRequestDTO) {
    try {
      NoticeResponseDto updatedNotice = noticeService.updateNotice(id, noticeRequestDTO);
      return ResponseEntity.ok(updatedNotice); // 정상 수정 시 200 OK 반환

    } catch(RuntimeException e) {
      return ResponseEntity.notFound().build(); // 404 NOT FOUND 반환
    }

  }

  // 공지사항 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
    try {
      noticeService.deleteNotice(id);
      return ResponseEntity.noContent().build(); // 삭제 완료 시 204 No Content 반환

    } catch(RuntimeException e) {
      return ResponseEntity.notFound().build(); // 삭제 실패 시 404 NOT FOUND 반환
    }
  }

}
