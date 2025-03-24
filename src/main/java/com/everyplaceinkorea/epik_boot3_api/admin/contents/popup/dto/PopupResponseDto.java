package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PopupResponseDto {
    //응답시 필요한 데이터
    private String title;
    private String content;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String snsLink;
    private String webLink;
    private boolean isActive;
    //    private Long popupTag;
    private String[] tags;
    private String writer;
    private LocalDateTime writeDate;
    private String addressDetail;
    private String[] saveImageNames; // 첨부파일 파일명

//    private List<PopupImageDto> images;
}
