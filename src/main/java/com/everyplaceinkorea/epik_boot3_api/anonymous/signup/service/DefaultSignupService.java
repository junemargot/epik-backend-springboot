package com.everyplaceinkorea.epik_boot3_api.anonymous.signup.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.EmailCheckDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.NicknameCheckDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.SignupRequestDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.UsernameCheckDto;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class DefaultSignupService implements SignupService {


    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Autowired
    private JavaMailSender mailSender;


    public DefaultSignupService(ModelMapper modelMapper, MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    // 1. username 중복 확인
    @Override
    public UsernameCheckDto usernameCheck(UsernameCheckDto usernameCheckDto) {

        UsernameCheckDto dto = new UsernameCheckDto();

        String username = usernameCheckDto.getUsername();
        System.out.println("username-"+username);

        Optional<Member> userCheck = memberRepository.findByUsername(username);

        if(userCheck.isPresent()) {
            dto.setUsername(username);
        } else {
            dto.setUsername(null);
        }
        return dto;
    }

    //2. nickname 중복확인
    @Override
    public NicknameCheckDto nicknameCheck(NicknameCheckDto nicknameCheckDto) {
        NicknameCheckDto dto = new NicknameCheckDto();

        String nickname = nicknameCheckDto.getNickname();
        System.out.println("nickname"+nickname);

       Optional<Member> nicknameCheck = memberRepository.findByNickname(nickname);
        System.out.println(nicknameCheck);

       if(nicknameCheck.isPresent()) {
           dto.setNickname(nickname);
           System.out.println(dto.getNickname());
       } else {
           dto.setNickname(null);
       }
        return dto;
    }




    //3. 인증이메일전송
    @Override
    public Map<String, String> emailCheck(EmailCheckDto emailCheckDto) {
        Map<String, String> response = new HashMap<>();

        String email = emailCheckDto.getEmail();

        // 1. 이메일 중복 확인
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        System.out.println(existingMember);

        if(existingMember.isPresent()) {
            response.put("message", "error");
        } else {
            response.put("message", "ok");

            // 2. 인증번호 생성
            String verificationCode = generateVerificationCode();

            // 3. 이메일 발송
            boolean verificationEmail = sendVerificationEmail(email, verificationCode);

            if (verificationEmail) {
                response.put("verificationCode", verificationCode);
            } else {
                response.put("verificationCode", null);
            }
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

    //4. 회원가입 최종 버튼
    @Override
    public SignupRequestDto signup(SignupRequestDto signupRequestDto) {
        System.out.println("signupRequestDto == " + signupRequestDto);
        Member member = modelMapper.map(signupRequestDto, Member.class);
        System.out.println("member == " + member);
        member.setRole("ROLE_MEMBER");
//        member.setJoinDate(new Timestamp(System.currentTimeMillis()));va
        member.setJoinDate(LocalDate.now());
        member.setType((byte) 1);
        member.setProfileImg("basic.png");

        //비밀번호 인코딩 //if = pw ===null, if문 추가 필요
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        Member saved= memberRepository.save(member);
        System.out.println("saved ==" + saved);
        //가입 타입, 마지막 방문 기록 기능 추가 필요
        return modelMapper.map(saved, SignupRequestDto.class);
    }
}
