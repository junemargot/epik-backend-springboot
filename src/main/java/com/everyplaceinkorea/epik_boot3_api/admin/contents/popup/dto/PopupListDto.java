package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PopupListDto {
    // 목록을 구성하는 화면에 필요한 데이터들
    private List<PopupDto> popupList;

    // 페이지네이션 관련 필드
    private Long totalCount;      // 총 게시물수
    private int totalPages;       // 게시물 전체 개수
    private Boolean hasNext;      // 다음 페이지
    private Boolean hasPrev;      // 이전 페이지
    private List<Long> pages;     // 표시될 페이지
}
