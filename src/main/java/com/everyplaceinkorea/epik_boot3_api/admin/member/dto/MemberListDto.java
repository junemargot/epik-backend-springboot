package com.everyplaceinkorea.epik_boot3_api.admin.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDto {
  private List<MemberDto> memberList;
  // 페이징 관련 필드
  private long totalCount;
  private int totalPages;
  private Boolean hasNext;
  private Boolean hasPrev;
  private List<Long> pages;

}
