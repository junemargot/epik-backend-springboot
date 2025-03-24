package com.everyplaceinkorea.epik_boot3_api.member.mypage.password.controller;

import com.everyplaceinkorea.epik_boot3_api.auth.dto.AuthResponseDto;
import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.password.dto.PasswordChechRequestDto;
import com.everyplaceinkorea.epik_boot3_api.member.mypage.password.service.PasswordUpdateService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mypage")
public class PasswordUpdateController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUpdateService passwordUpdateService;

    public PasswordUpdateController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("passwordCheck")
    public String passwordCheck(@RequestBody PasswordChechRequestDto passwordChechRequestDto ,HttpServletResponse response, HttpServletRequest request) {
        Boolean passwordCheckResult = passwordUpdateService.passwordCheck(passwordChechRequestDto);
        System.out.println("passwordCheckResult-"+passwordCheckResult);

        return passwordCheckResult.toString();
    }

    @PostMapping("updatepassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChechRequestDto passwordChechRequestDto ,HttpServletResponse response, HttpServletRequest request) {

        EpikUserDetails userDetails =(EpikUserDetails) passwordUpdateService.passwordUpdate(passwordChechRequestDto);
        System.out.print(userDetails);

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

        System.out.println("변경된 토큰-"+token);

        return ResponseEntity.ok(responseDto);

    }

}
