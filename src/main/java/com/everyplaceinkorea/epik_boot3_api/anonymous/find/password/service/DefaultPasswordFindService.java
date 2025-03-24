package com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.dto.PasswordFindRequestDto;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultPasswordFindService implements PasswordFindService {

    MemberRepository memberRepository;

    DefaultPasswordFindService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public String checkUsername(PasswordFindRequestDto passwordFindRequestDto) {

        System.out.println("passwordFindRequestDto--"+passwordFindRequestDto.getUsername());

        Optional<Member> usernameMember = memberRepository.findByUsername(passwordFindRequestDto.getUsername());
        String usernameFind = null ;

        if (usernameMember.isPresent()) {
            Member member = usernameMember.get();
            usernameFind = member.getUsername();
            return usernameFind;
        } else {
            usernameFind = "notfound";
            System.out.println("등록된 이메일 멤버 없음");
        }
        return usernameFind;

    }

    @Override
    public String passwordReset(PasswordFindRequestDto passwordFindRequestDto) {
        System.out.println("전달받은 username값-"+ passwordFindRequestDto);

        Optional<Member> member = memberRepository.findByUsername(passwordFindRequestDto.getUsername());
        Member existingMember = member.get();
        System.out.println("원래 디비에 있던 비밀번호--"+existingMember.getPassword());
        String passwordRest = null;
        if (member.isPresent()) {

            System.out.println("원래 디비에 있던 비밀번호--"+existingMember.getPassword());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newEncodedPassword = passwordEncoder.encode(passwordFindRequestDto.getPassword());
            System.out.println("입력받은 새비밀번호 인코딩 값--"+newEncodedPassword);
            existingMember.setPassword(newEncodedPassword);
            memberRepository.save(existingMember);
            passwordRest = "success";

        } else{
            System.out.println("업데이트할 멤버 없음");
            passwordRest = "failed";};
            return passwordRest;
    }

}
