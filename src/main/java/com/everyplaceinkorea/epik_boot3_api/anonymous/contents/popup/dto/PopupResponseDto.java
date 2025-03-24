package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PopupResponseDto {
    //번호
    private Long id;
    //지역
    private String region;
    //대표이미지
    private String filePath;
    //상태
    private String status;
    //제목
    private String title;

    private String content;
    //시작일
    private LocalDate startDate;
    //종료일
    private LocalDate endDate;
    //장소
    private String address;
    //상세장소
    private String addressDetail;
    //태그
    private String[] tags;
    //이미지
    private String[] saveImageNames;

    private String imgSavedName;

    private String snsLink;

    private String webLink;
}
