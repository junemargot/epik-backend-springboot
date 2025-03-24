package com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.service;

import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.InfoRequestDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.InfoResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.ProfilePicRequestDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.ProfilePicResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface InfoService {

    //프로필 사진 url 전달
    ProfilePicResponseDto updateProfilePic(ProfilePicRequestDto profilePicRequestDto);

    UserDetails updateInfo(InfoRequestDto infoRequestDto);
}
