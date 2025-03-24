package com.everyplaceinkorea.epik_boot3_api.admin.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
  // 목록 화면을 위한 데이터들
  private Long id;
  private String title;
  private String content;
  private LocalDateTime writeDate;
  private String writer;
  private Integer viewCount;
}
