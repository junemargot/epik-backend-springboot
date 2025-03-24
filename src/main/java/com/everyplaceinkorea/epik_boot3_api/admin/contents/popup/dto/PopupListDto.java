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
    // 필요한 데이터
    // 번호, 제목, 작성자, 등록일, 조회수, 총 게시물 수
//    private int id;
//    private String title;
//    private String writer;
//    private LocalDate writeDate;
//    private Integer viewCount;
    private List<PopupDto> popupList;
    private Long totalCount;
}
