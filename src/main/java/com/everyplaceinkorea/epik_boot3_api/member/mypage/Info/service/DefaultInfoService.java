package com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.service;

import com.everyplaceinkorea.epik_boot3_api.admin.member.service.MemberService;
import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.InfoRequestDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.InfoResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.ProfilePicRequestDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.Info.dto.ProfilePicResponseDto;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultInfoService implements InfoService {

    private final MemberRepository memberRepository;
    private Member member;

    public DefaultInfoService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public ProfilePicResponseDto updateProfilePic(ProfilePicRequestDto profilePicRequestDto)
    {

        return null;
    }

    @Override
    public UserDetails updateInfo(InfoRequestDto infoRequestDto) {

        //프런트에서 들어오는 정보 확인
        System.out.println("infoRequestDto == " + infoRequestDto);

        Optional<Member> member = memberRepository.findById(infoRequestDto.getId());
        Member existingMember = member.get();
        if (member.isPresent()) {

            existingMember.setEmail(infoRequestDto.getEmail());
            existingMember.setNickname(infoRequestDto.getNickname());
            memberRepository.save(existingMember);
        } else
            System.out.println("업데이트할 멤버 없음");

        String role = existingMember.getRole();
        System.out.println("role-"+role);
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        return EpikUserDetails.builder()
                .id(existingMember.getId())
                .username(existingMember.getUsername())
                .password(existingMember.getPassword())
                .email(existingMember.getEmail())
                .nickname(existingMember.getNickname())
                .authorities(authorities)
                .build();
    }


}
