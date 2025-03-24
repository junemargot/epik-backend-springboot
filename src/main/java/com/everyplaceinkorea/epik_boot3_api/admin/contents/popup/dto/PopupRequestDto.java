package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupImage;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupTag;
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
public class PopupRequestDto {
    private String title;
    private String address;
    private String addressDetail;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String operationTime;
    private String snsLink;
    private String webLink;
    private Long popupCategory;
    private Long popupRegion;
    private boolean isActive;
    private String[] tags;
    private Long writer;
    private LocalDateTime writeDate;
    private String[] fileNames; // 업로드한 파일명
    private String type;
}
