package com.everyplaceinkorea.epik_boot3_api.anonymous.notice.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {

  private Long id;
  private String title;
  private String content;
  private LocalDateTime writeDate;
  private String writer;

  // 페이지네이션 관련
  private List<NoticeResponseDto> noticeList;
  private long totalCount;
  private long totalPages;
  private Boolean hasPrev;
  private Boolean hasNext;
  private List<Long> pages;
}
