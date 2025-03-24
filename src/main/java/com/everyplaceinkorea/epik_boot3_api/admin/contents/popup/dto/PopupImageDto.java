package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupImageDto {
    private Integer imgOrder;  // 이지미 순서
    private String imgSavedName; // 저장된 이미지 경로
}
