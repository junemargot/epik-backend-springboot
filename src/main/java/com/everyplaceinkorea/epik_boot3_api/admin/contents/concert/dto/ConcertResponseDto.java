package com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponseDto {
  // 응답받을 때 필요한 데이터
  private Long id;                 // 콘서트 식별 번호
  private String title;            // 공연 이름
  private String content;          // 내용
  private String writer;           // 작성자
  private String venue;            // 공연장
  private String address;          // 공연장 상세주소
  private LocalDate startDate;     // 시작일 -- YYYY.MM.DD
  private LocalDate endDate;       // 종료일
  private String runningTime;      // 공연시간
  private String ageRestriction;   // 관람연령
  private String saveImageName;    // 이미지
  private LocalDateTime writeDate; // 작성일
  private List<ConcertTicketPriceDto> ticketPrices;    // 티켓가격
  private List<ConcertTicketOfficeDto> ticketOffices;  // 티켓판매
  private String youtubeUrl;       // 유튜브 url
}
