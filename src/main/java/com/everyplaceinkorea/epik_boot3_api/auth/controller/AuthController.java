package com.everyplaceinkorea.epik_boot3_api.auth.controller;


import com.everyplaceinkorea.epik_boot3_api.auth.dto.*;
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

/**
 * AuthController는 인증과 관련된 REST API 엔드포인트를 제공
 * 이 컨트롤러는 일반 로그인과 구글 로그인 기능을 지원
*/
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;  // 인증 처리를 위한 AuthenticationManager (스프링 시큐리티가 내부적으로 사용)
    private JwtUtil jwtUtil; // JWT 관련 유틸리티 클래스 (토큰 생성, 검증 등)

    @Autowired
    private MemberRepository memberRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 일반 로그인 API
     * 클라이언트로부터 JSON 형식으로 전달받은 username과 password를 기반으로 인증을 수행합니다.
     *
     * @param requestDto 로그인 요청 정보 (username, password)
     * @param response HttpServletResponse 객체를 통해 쿠키에 JWT 토큰을 저장합니다.
     * @param request  HttpServletRequest 객체 (필요한 경우 추가 정보 확인)
     * @return 인증 성공 시 AuthResponseDto (회원 ID 및 JWT 토큰) 반환, 실패 시 401 UNAUTHORIZED 응답
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response, HttpServletRequest request) {

        // 1. 클라이언트에서 전달받은 username과 password 저장
        String username = requestDto.getUsername();
        System.out.println(username);
        String password = requestDto.getPassword();
        System.out.println(password);

        // 2. username과 password를 사용하여 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            // 3. AuthenticationManager를 통해 인증 진행
            // 이 과정에서 DaoAuthenticationProvider가 실행되고, UserDetailsService를 이용하여 사용자 정보를 조회
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 4. 인증에 성공하면, Principal 객체(EpikUserDetails)를 가져옴
            EpikUserDetails userDetails = (EpikUserDetails) authentication.getPrincipal();

            // 5. JWT 유틸리티를 사용해 사용자 정보를 기반으로 JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);
            System.out.println("Generated Token: " + token);

            // 6. JWT 토큰을 HttpOnly, Secure 쿠키에 저장하여 클라이언트로 전송
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);       // JavaScript에서 접근 제한
            cookie.setSecure(true);         // HTTPS 전용
            cookie.setPath("/");            // 모든 경로에서 유효
            cookie.setMaxAge(60 * 60 * 24); // 1일 보존
            response.addCookie(cookie);

            // 7. AuthResponseDto 생성: 회원 ID와 토큰 포함
            AuthResponseDto responseDto = AuthResponseDto
                    .builder()
                    .memberId(userDetails.getId())
                    .token(token) // 여기에 인증 상태 정보를 모두 담아서 전달함
                    .build();

            System.out.println("토큰-"+responseDto);
            return ResponseEntity.ok(responseDto);

        } catch(AuthenticationException e) {
            System.out.println("예외이유: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    /**
     * 구글 로그인 API (Google OAuth 2.0 로그인 기능)
     * 구글 계정 정보를 클라이언트로부터 받아 회원 가입 또는 조회 후, JWT 토큰을 발급합니다.
     *
     * @param googleRequestDto 구글에서 받은 계정 정보 (email, id, name)
     * @param response HttpServletResponse 객체 (쿠키에 토큰 저장)
     * @param request  HttpServletRequest 객체
     * @return AuthResponseDto 형태로 회원 ID와 JWT 토큰 반환
     */
    @PostMapping("google-login")
//    @PostMapping("/login/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleRequestDto googleRequestDto, HttpServletResponse response, HttpServletRequest request) {

        // 1. 구글 계정 정보 추출 (email, id, name)
        String email = googleRequestDto.getEmail();
        String id = googleRequestDto.getId();
        String name = googleRequestDto.getName();
        System.out.println("입력된 구글 정보 확인- " + email + id + name);

        // 2. 임의의 단기 식별자를 생성하기 위해 4자리 랜덤 숫자 생성
        StringBuilder randomNumbers = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자
            randomNumbers.append(randomDigit);
        }
        Long shortId = Long.parseLong(randomNumbers.toString());
        System.out.println("정리된 구글 정보 확인(shortId)- " + email + shortId + name);
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        // 3. 기존에 해당 이메일로 등록된 회원이 있는지 체크
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        // 4. 회원이 없으면 신규 가입 처리
        Member member = new Member();
        if (memberOptional.isEmpty()) {
            member.setId(shortId);
            member.setUsername(email);
            member.setNickname(name);
            member.setEmail(email);
            member.setJoinDate(LocalDate.now());
            member.setType((byte) 1);  // 임의의 타입 값
            member.setRole("ROLE_MEMBER");
            memberRepository.save(member);
            System.out.println("비회원, 가입 완료");
        } else {
            // 5. 이미 회원이면 기존 회원 데이터 사용
            member = memberOptional.get();
            System.out.println("회원입니다");
        }

        System.out.println("member-" + member.getRole());
        System.out.println("id-" + member.getId() + "username-" + member.getUsername() + "nickname" + member.getNickname());

        // 6. JWT 토큰 생성을 위해 EpikUserDetails 객체 생성 (인증 정보 객체)
        EpikUserDetails userDetails = EpikUserDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
                .build();

        // 7. JwtUtil을 통해 토큰 생성
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("토큰출력-" + token);

        // 8. 생성된 토큰을 쿠키에 저장 (HTTP 전용)
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
        cookie.setSecure(true);   // HTTPS에서만 전송하도록 설정
        cookie.setPath("/");      // 쿠키가 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(60 * 60 * 24); // 1일 동안 쿠키 유지
        response.addCookie(cookie);

        // 9. GoogleResponseDto 생성 후, JWT 토큰과 함께 반환
        GoogleResponseDto responseDto = GoogleResponseDto
                .builder()
                .memberId(userDetails.getId())
                .token(token)
                .build();

        System.out.println("리스판스 확인-" + responseDto);

        return ResponseEntity.ok(responseDto);
    }
}
