package com.everyplaceinkorea.epik_boot3_api.member.mypage.password.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChechRequestDto {
    private Long id;
    private String password;

}
