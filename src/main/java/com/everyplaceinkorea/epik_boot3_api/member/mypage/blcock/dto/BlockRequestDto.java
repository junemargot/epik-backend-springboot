package com.everyplaceinkorea.epik_boot3_api.member.mypage.blcock.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockRequestDto {
    private String username;
}
