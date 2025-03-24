package com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePicRequestDto {
    private Long id;
    private String profileImg;

}
