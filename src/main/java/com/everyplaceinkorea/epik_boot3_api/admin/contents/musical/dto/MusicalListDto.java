package com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MusicalListDto {
    private List<MusicalDto> musicalList;

    // 페이징 관련 핃드
    private long totalCount;      // 총 게시물수
    private int totalPages;       // 게시물 전체 개수
    private Boolean hasNext;      // 다음 페이지
    private Boolean hasPrev;      // 이전 페이지
    private List<Long> pages;     // 표시될 페이지

}
