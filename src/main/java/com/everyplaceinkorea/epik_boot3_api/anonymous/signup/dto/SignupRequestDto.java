package com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String profileImg;
    private String profileText;


}
