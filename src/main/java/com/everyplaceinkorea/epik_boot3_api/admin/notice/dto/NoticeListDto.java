package com.everyplaceinkorea.epik_boot3_api.admin.notice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListDto {
  // 목록을 구성하는 화면에 필요한 데이터들
  private List<NoticeResponseDto> noticeList;

  // 페이징 관련 필드
  private long totalCount; // 게시물 전체 개수
  private int totalPages; // 전체 페이지 Long -> int로 변경
  private Boolean hasNext; // 다음 페이지
  private Boolean hasPrev; // 이전 페이지
  private List<Long> pages; // 표시될 페이지 Long -> Integer로 변경

}
