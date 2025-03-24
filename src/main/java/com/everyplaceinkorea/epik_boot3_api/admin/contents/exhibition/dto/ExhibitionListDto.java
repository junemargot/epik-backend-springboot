package com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto.ConcertDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionListDto {
  // 목록을 구성하는 화면에 필요한 데이터들
  private List<ExhibitionDto> exhibitionList;

  // 페이징 관련 핃드
  private long totalCount;      // 총 게시물수 -- 한번만 데이터 뽑아오기 위해
  private int totalPages;       // 게시물 전체 개수
  private Boolean hasNext;      // 다음 페이지
  private Boolean hasPrev;      // 이전 페이지
  private List<Long> pages;     // 표시될 페이지

}
