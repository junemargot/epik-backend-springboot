package com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordFindRequestDto {

    private String username;
    private String password;
}
