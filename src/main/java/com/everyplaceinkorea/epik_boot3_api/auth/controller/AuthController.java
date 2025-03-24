package com.everyplaceinkorea.epik_boot3_api.auth.controller;


import com.everyplaceinkorea.epik_boot3_api.auth.dto.AuthRequestDto;
import com.everyplaceinkorea.epik_boot3_api.auth.dto.AuthResponseDto;
import com.everyplaceinkorea.epik_boot3_api.auth.dto.GoogleRequestDto;
import com.everyplaceinkorea.epik_boot3_api.auth.dto.GoogleResponseDto;
import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepository memberRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response, HttpServletRequest request) {

        String username = requestDto.getUsername();
        System.out.println(username);
        String password = requestDto.getPassword();
        System.out.println(password);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            // 회원 : 비회원 인증 수행
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //authenticationManager는 DaoAuthenticationProvider(UserDetailsService -> EpikUserDetailsService)를 사용하여 인증을 처리
            // UserDetailsService를 통해서 사용자 정보를 받아서 인증을 처리한 후
            // 인증에 성공하게 되면 SecurityContextHolder에 사용자 인증 정보를 담아 놓게 됨.

            // 성공 시 사용자 정보 가져오기
            EpikUserDetails userDetails = (EpikUserDetails) authentication.getPrincipal();

            String token = jwtUtil.generateToken(userDetails); // 사용자 정보 가져와 토튼 만들기
            System.out.println("Generated Token: " + token); // JWT 토큰 출력

            // 토큰을 쿠키에 저장
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
            cookie.setSecure(true);   // HTTPS에서만 전송하도록 설정
            cookie.setPath("/");      // 쿠키가 모든 경로에서 유효하도록 설정
            cookie.setMaxAge(60 * 60 * 24); // 1일 동안 쿠키 유지
            // 쿠키를 응답에 추가
            response.addCookie(cookie);


            // JWT 토큰 반환
            AuthResponseDto responseDto = AuthResponseDto
                    .builder()
                    .memberId(userDetails.getId())
                    .token(token) // 여기에 인증 상태 정보를 모두 담아서 전달함
                    .build();

            System.out.println("토큰-"+responseDto);

                return ResponseEntity.ok(responseDto);

        }
        catch (AuthenticationException e) {
            System.out.println("예외이유 " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleRequestDto googleRequestDto, HttpServletResponse response, HttpServletRequest request) {

        String email = googleRequestDto.getEmail();
        String id = googleRequestDto.getId();
        String name = googleRequestDto.getName();
        System.out.println("입력된 구글 정보 확인- "+email+ id + name);

        // 랜덤 숫자 4개를 저장할 StringBuilder
        StringBuilder randomNumbers = new StringBuilder();
        Random random = new Random();
        // 4번 반복하여 숫자 0-9 사이의 랜덤 값 선택
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자
            randomNumbers.append(randomDigit);
        }
        Long shortId = Long.parseLong(randomNumbers.toString());
        System.out.println("정리된 구글 정보 확인(shortId)- "+email+ shortId + name);
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();


        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        Member member = new Member();
        if (memberOptional.isEmpty()) {
            member.setId(shortId);
            member.setUsername(email);
            member.setNickname(name);
            member.setEmail(email);
            member.setJoinDate(LocalDate.now());
            member.setType((byte)1);  // 예시 값
            member.setRole("ROLE_MEMBER");
            memberRepository.save(member);
            System.out.println("비회원, 가입 완료");
        }  {
            member = memberOptional.get();
            System.out.println("회원입니다");

        }


        System.out.println("member-"+member.getRole());
        System.out.println("id-"+ member.getId()+ "username-"+ member.getUsername()+"nickname"+member.getNickname());

        //토큰 만들 준비
        EpikUserDetails userDetails = EpikUserDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
                .build();

        String token = jwtUtil.generateToken(userDetails);
        System.out.println("토큰출력-"+token);

        // 토큰을 쿠키에 저장
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
        cookie.setSecure(true);   // HTTPS에서만 전송하도록 설정
        cookie.setPath("/");      // 쿠키가 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(60 * 60 * 24); // 1일 동안 쿠키 유지
        // 쿠키를 응답에 추가
        response.addCookie(cookie);

        GoogleResponseDto responseDto = GoogleResponseDto
                .builder()
                .memberId(userDetails.getId())
                .token(token) // 여기에 인증 상태 정보를 모두 담아서 전달함
                .build();

        System.out.println("리스판스 확인-"+responseDto);

        return ResponseEntity.ok(responseDto);
    }



}
