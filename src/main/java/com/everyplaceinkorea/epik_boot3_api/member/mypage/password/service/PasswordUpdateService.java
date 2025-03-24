package com.everyplaceinkorea.epik_boot3_api.member.mypage.password.service;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.password.dto.PasswordChechRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PasswordUpdateService {

    Boolean passwordCheck(PasswordChechRequestDto passwordChechRequestDto);

    UserDetails passwordUpdate(PasswordChechRequestDto passwordChechRequestDto);


}
