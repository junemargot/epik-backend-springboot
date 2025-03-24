package com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertTicketPriceDto {
  private Long id;
  private String seat;      // 티켓 해당 좌석명
  private String price;    // 티켓 금액
  private Long concertId;   // 공연 정보 FK
}
