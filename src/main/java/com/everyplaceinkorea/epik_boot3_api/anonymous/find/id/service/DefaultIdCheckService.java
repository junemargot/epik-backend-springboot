package com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.dto.IdCheckRequestDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.EmailCheckDto;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class DefaultIdCheckService implements IdCheckService {

    MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;

    DefaultIdCheckService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //인증이메일전송
    @Override
    public Map<String, String> emailCheck(EmailCheckDto emailCheckDto) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "ok");

        String email = emailCheckDto.getEmail();
        System.out.println("emailtest-"+emailCheckDto.getEmail());

            String verificationCode = generateVerificationCode();

            boolean verificationEmail = sendVerificationEmail(email, verificationCode);

            if (verificationEmail) {
                response.put("verificationCode", verificationCode);
            } else {
                response.put("verificationCode", null);
            }

        return response;
    }

    // 인증번호 생성 (예: 6자리 랜덤 코드)
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // 알파벳 3자리 + 숫자 6자리
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(26) + 65;  // 알파벳 대문자
            code.append((char) index);
        }
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);  // 숫자 0-9
            code.append(num);
        }

        return code.toString();
    }

    // 이메일 발송 메서드
    private boolean sendVerificationEmail(String email, String verificationCode) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);  // 수신 이메일
        message.setSubject("회원가입 이메일 인증번호");
        message.setText("회원가입을 위한 인증번호는 " + verificationCode + "입니다.");

        mailSender.send(message);  // 메일 발송
        return true;

    }


    @Override
    public String idCheck(IdCheckRequestDto idCheckRequestDto) {
        System.out.println("IdcheckRequestdto-" + idCheckRequestDto);

        Optional<Member> emailMember = memberRepository.findByEmail(idCheckRequestDto.getEmail());
        String memberUsetname = null;

        if (emailMember.isPresent()) {
            Member member = emailMember.get();
            memberUsetname = member.getUsername();
        } else {
            memberUsetname = "failed";
            System.out.println("등록된 이메일 멤버 없음");
        }
        return memberUsetname;
    }




}
