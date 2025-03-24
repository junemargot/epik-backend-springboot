package com.everyplaceinkorea.epik_boot3_api.member.mypage.password.service;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.password.dto.PasswordChechRequestDto;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultPasswordUpdateService implements PasswordUpdateService {

    private final MemberRepository memberRepository;

    public DefaultPasswordUpdateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //현재비밀번호 확인
    @Override
    public Boolean passwordCheck(PasswordChechRequestDto passwordChechRequestDto) {
        //받은 값 확인
        System.out.println(passwordChechRequestDto);

        //받은 값 디비 존재 여부 확인
        Optional<Member> member = memberRepository.findById(passwordChechRequestDto.getId());
        Member existingMember = member.get();
        System.out.println("원래 디비에 있던 비밀번호--"+existingMember.getPassword());
        System.out.println("비밀번호 확인--"+passwordChechRequestDto.getPassword());

        //받은 비밀번호 인코딩
        // 비밀번호 비교 (BCryptPasswordEncoder의 matches() 사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 입력된 비밀번호와 DB에 저장된 인코딩된 비밀번호를 비교합니다.
        boolean matches = passwordEncoder.matches(passwordChechRequestDto.getPassword(), existingMember.getPassword());

        System.out.println("비밀번호 boolean 결과값-"+matches);
        return matches;
    }

    //새로운 비밀번호 업데이트
    @Override
    public UserDetails passwordUpdate(PasswordChechRequestDto passwordChechRequestDto) {
        //받은 값 확인
        System.out.println("passwordChechRequestDto-"+ passwordChechRequestDto);

        //받은 값 디비 존재 여부 확인
        Optional<Member> member = memberRepository.findById(passwordChechRequestDto.getId());
        Member existingMember = member.get();
        System.out.println("원래 디비에 있던 비밀번호--"+existingMember.getPassword());

        if (member.isPresent()) {

            System.out.println("원래 디비에 있던 비밀번호--"+existingMember.getPassword());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newEncodedPassword = passwordEncoder.encode(passwordChechRequestDto.getPassword());
            System.out.println("입력받은 새비밀번호 인코딩 값--"+newEncodedPassword);
            existingMember.setPassword(newEncodedPassword);

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
