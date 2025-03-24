package com.everyplaceinkorea.epik_boot3_api.admin.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDto {
  // 요청 보낼 때 필요한 데이터를 담는 곳.
  private String title; // 공지사항 제목
  private String content; // 공지사항 내용
  private Long writer;

}