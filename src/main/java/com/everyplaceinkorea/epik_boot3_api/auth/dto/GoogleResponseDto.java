package com.everyplaceinkorea.epik_boot3_api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleResponseDto {
    private Long memberId;
    private String token;
}
