package com.everyplaceinkorea.epik_boot3_api.auth.controller;


import com.everyplaceinkorea.epik_boot3_api.auth.dto.*;
import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.auth.util.JwtUtil;
import com.everyplaceinkorea.epik_boot3_api.entity.member.LoginType;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.*;

/**
 * AuthController는 인증과 관련된 REST API 엔드포인트를 제공
 * 이 컨트롤러는 일반 로그인과 구글 로그인, 카카오 로그인, 네이버 로그인 기능을 지원
*/
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;  // 인증 처리를 위한 AuthenticationManager (스프링 시큐리티가 내부적으로 사용)
    private final JwtUtil jwtUtil; // JWT 관련 유틸리티 클래스 (토큰 생성, 검증 등)

    @Autowired
    private MemberRepository memberRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 일반 로그인 API
     * 클라이언트로부터 JSON 형식으로 전달받은 username과 password를 기반으로 인증을 수행하고,
     * 인증에 성공하면 JWT 토큰을 생성하여 HTTP 쿠키에 저장한 후 응답으로 반환한다.
     *
     * @param requestDto 로그인 요청 정보 (username, password)
     * @param response HttpServletResponse 객체를 (쿠키에 JWT 토큰을 저장)
     * @param request  HttpServletRequest 객체 (필요 시 추가 정보 확인)
     * @return 인증 성공 시 AuthResponseDto (회원 ID 및 JWT 토큰) 반환, 실패 시 401 UNAUTHORIZED 응답
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response, HttpServletRequest request) {

        // 1. 클라이언트에서 전달받은 username과 password 저장
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 2. 인증을 위한 토큰 객체 생성 (UsernamePasswordAuthenticationToken)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            // 3. AuthenticationManager를 통해 인증 절차 수행
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 4. 인증에 성공하면, 인증 정보를 담은 Principal 객체(EpikUserDetails)를 획득
            EpikUserDetails userDetails = (EpikUserDetails) authentication.getPrincipal();

            // 5. JWT 유틸리티를 사용해 사용자 정보를 기반으로 JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);
            System.out.println("Generated Token: " + token);

            // 6. 생성된 토큰을 HttpOnly, Secure 설정이 적용된 쿠키에 저장
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);       // 클라이언트의 JavaScript에서 쿠키 접근 제한
            cookie.setSecure(true);         // HTTPS 환경에서만 전송
            cookie.setPath("/");            // 모든 경로에서 유효
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 만료 시간: 1일
            response.addCookie(cookie);

            // 7. 회원 ID와 토큰을 포함한 응답 DTO 생성 후 반환
            AuthResponseDto responseDto = AuthResponseDto
                    .builder()
                    .memberId(userDetails.getId())
                    .token(token)
                    .build();

            return ResponseEntity.ok(responseDto);

        } catch(AuthenticationException e) {
;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    /**
     * 구글 로그인 API (Google OAuth 2.0 로그인 기능)
     * 구글 계정 정보를 클라이언트로부터 받아 회원 가입 또는 조회 후,
     * JWT 토큰을 생성하여 쿠키에 저장하고 응답으로 반환한다.
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
            member.setLoginType(LoginType.GOOGLE);
            memberRepository.save(member);
            System.out.println("비회원, 구글 소셜로그인 가입 완료");
        } else {
            member = memberOptional.get();
            System.out.println("구글 회원입니다");
        }

//        System.out.println("member-" + member.getRole());
//        System.out.println("id-" + member.getId() + "username-" + member.getUsername() + "nickname" + member.getNickname());

        // 5. JWT 토큰 생성을 위해 EpikUserDetails 객체 생성 (인증 정보 객체)
        EpikUserDetails userDetails = EpikUserDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
                .build();

        // 6. JWT 유틸리티를 사용하여 토큰 생성
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("토큰출력-" + token);

        // 7. 생성된 토큰을 쿠키에 저장 (HTTP 전용)
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);       // JavaScript에서 접근할 수 없도록 설정
        cookie.setSecure(true);         // HTTPS에서만 전송하도록 설정
        cookie.setPath("/");            // 쿠키가 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(60 * 60 * 24); // 1일 동안 쿠키 유지
        response.addCookie(cookie);

        // 8. GoogleResponseDto 생성 후, JWT 토큰과 함께 반환
        GoogleResponseDto responseDto = GoogleResponseDto
                .builder()
                .memberId(userDetails.getId())
                .token(token)
                .build();

        System.out.println("리스판스 확인-" + responseDto);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 카카오 로그인 API (Kakao OAuth 2.0 로그인 기능)
     * 클라이언트로부터 전달받은 Authorization 헤더의 액세스 토큰을 통해
     * 카카오 API를 호출하여 사용자 정보를 받아오고,
     * 회원가입 또는 조회를 진행한 후 JWT 토큰과 프로필 이미지를 반환함.
     *
     * @param authHeader Authorization 헤더 (Bearer 토큰)
     * @param response HttpServletResponse 객체 (쿠키에 토큰 저장에 사용)
     * @return JWT 토큰과 프로필 이미지 URL이 포함된 응답을 반환
     */
    @PostMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestHeader("Authorization") String authHeader, HttpServletResponse response) {

        try {
            // 1. Authorization 헤더에서 "Bearer " 접두사를 제거하여 액세스 토큰만 추출
            String accessToken = authHeader.replace("Bearer ", "");
            System.out.println("Kakao 액세스 토큰: " + accessToken);

            // 2. RestTemplate를 사용하여 카카오 API 호출 준비
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            // 헤더에 카카오 액세스 토큰을 Bearer 방식으로 추가
            headers.add("Authorization", "Bearer " + accessToken);

            // 3. 카카오 API를 호출하여 사용자 정보 응답 받기
            ResponseEntity<Map> kakaoResponse = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",   // 카카오 사용자 정보 API 엔드포인트
                    HttpMethod.GET,                            // HTTP GET 방식 호출
                    new HttpEntity<>(headers),                 // 헤더 정보 포함된 HttpEntity
                    Map.class                                  // 응답 결과를 Map 형태로 파싱
            );
            System.out.println("카카오 API 응답: " + kakaoResponse.getBody());

            // 4. 카카오 API 응답에서 사용자 정보 추출
            // "kakao_account" 객체에서 이메일 등의 정보를 얻고, "profile" 객체에서 프로필 관련 정보를 추출
             Map<String, Object> kakaoAccount = (Map)kakaoResponse.getBody().get("kakao_account");
            Map<String, Object> profile = (Map)kakaoAccount.get("profile");

            String email = (String) kakaoAccount.get("email");
            String nickname = (String) profile.get("nickname");
            String profileImage = (String) profile.get("profile_image_url");

            // 5. 프로필 이미지 동의하지 않을 경우 기본 이미지 경로 설정
            if(profileImage == null || profileImage.isEmpty()) {
                profileImage = "/images/basic.png"; // 기본 이미지 경로
            }

            final String finalProfileImage = profileImage; // 람다식 내부에서 사용하기 위한 final 변수

            // 6. 이메일을 기준으로 기존 회원 조회 후, 없으면 신규 회원 가입 처리
            Optional<Member> memberOptional = memberRepository.findByEmail(email);
            Member member = memberOptional.orElseGet(() -> {
                Member newMember = new Member();
                newMember.setUsername(email);
                newMember.setEmail(email);
                newMember.setNickname(nickname);
                newMember.setProfileImg(finalProfileImage);
                newMember.setJoinDate(LocalDate.now());
                newMember.setType((byte) 1);
                newMember.setRole("ROLE_MEMBER");
                newMember.setLoginType(LoginType.KAKAO);

                return memberRepository.save(newMember);
            });

            // 7. 기존 회원인 경우 프로필 이미지 업데이트 및 username 설정
            if(memberOptional.isPresent() && profileImage != null) {
                member.setProfileImg(profileImage);

                if(member.getUsername() == null || member.getUsername().isEmpty()) {
                    member.setUsername(email);
                }
                memberRepository.save(member);
            }

            // 8. JWT 토큰 생성을 위한 EpikUserDetails 객체 생성
            EpikUserDetails userDetails = EpikUserDetails.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .profileImage(member.getProfileImg())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
                    .build();

            String token = jwtUtil.generateToken(userDetails);

            // 디버깅을 위한 토큰 출력
            System.out.println("생성된 JWT 토큰: " + token);
            System.out.println("토큰 부분 개수: " + token.split("\\.").length);

            // 7. 응답 설정
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, createCookie(token).toString())
                    .body(Map.of(
                            "accessToken", token,
                            "profileImage", profileImage // 클라이언트에 이미지 url 반환 (선택사항)
                    ));
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "카카오 로그인 처리 중 오류 발생: " + e.getMessage()));
        }

//        // 1. 카카오 계정 정보 추출(email, id, name)
//        Long id = kakaoRequestDto.getId();
//        String email = kakaoRequestDto.getEmail();
//        String nickname = kakaoRequestDto.getNickname();
//        String profileImage = kakaoRequestDto.getProfileImage();
//
//        System.out.println("입력된 카카오 정보 확인 - ID: " + id + "Email: " + email + "Nickname: " + nickname + "ProfileImage: " + profileImage);
//
//        // 2. 기존에 해당 이메일로 등록된 회원이 있는지 체크
//        Optional<Member> memberOptional = memberRepository.findByEmail(email);
//
//        // 3. 회원이 없으면 신규 가입 처리
//        Member member;
//        if(memberOptional.isEmpty()) {
//            member = new Member();
//            member.setUsername(email); // 이메일을 username으로 사용
//            member.setNickname(nickname);
//            member.setEmail(email);
//            member.setProfileImg(profileImage); // 선택 동의 항목인 프로필 이미지 저장
//            member.setJoinDate(LocalDate.now());
//            member.setType((byte) 1); // 회원 유형: 일반 회원 설정
//            member.setRole("ROLE_MEMBER");
//            memberRepository.save(member);
//            System.out.println("[[[[[ 비회원, 가입 완료 ]]]]]");
//
//        } else {
//            // 이미 회원이면 기존 회원 데이터 사용
//            member = memberOptional.get();
//            System.out.println("[[[[ 회원입니다. ]]]]]");
//        }
//
//        System.out.println("member-" + member.getRole());
//        System.out.println("id: " + member.getId() + ", username: " + member.getUsername() + ", nickname: " + member.getNickname());
//
//        // 4. JWT 토큰 생성을 위해 EpikUserDetails 객체 생성 (인증 정보 객체)
//        EpikUserDetails userDetails = EpikUserDetails.builder()
//                .id(member.getId())
//                .username(member.getUsername())
//                .email(member.getEmail())
//                .nickname(member.getNickname())
//                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
//                .build();
//
//        // 5. JwtUtil을 통해 토큰 생성
//        String token = jwtUtil.generateToken(userDetails);
//        System.out.println("생성된 토큰: " + token);
//
//        // 6. 생성된 토큰을 쿠키에 저장 (HTTP 전용)
//        Cookie cookie = new Cookie("jwt_token", token);
//        cookie.setHttpOnly(true); // Javascript에서 접근할 수 없도록 설정
//        cookie.setSecure(true);   // HTTPS에서만 전송하도록 설정
//        cookie.setPath("/");      // 쿠키가 모든 경로에서 유효하도록 설정
//        cookie.setMaxAge(60 * 60 * 24); // 1일 동안 쿠키 유지
//        response.addCookie(cookie);
//
//        // 7. KakaoResponseDto 생성 후, JWT 토큰과 함께 반환
//        KakaoResponseDto responseDto = KakaoResponseDto.builder()
//                .memberId(userDetails.getId())
//                .accessToken(token)
//                .build();
//
//        System.out.println("카카오 최종 응답 확인: " + responseDto);
//
//        return ResponseEntity.ok(responseDto);
    }

    /**
     * JWT 토큰을 안전하게 저장하기 위해 HttpOnly, Secure 쿠키를 생성하는 헬퍼 메서드.
     *
     * @param token JWT 토큰 문자열
     * @return 생성된 ResponseCookie 객체
     */
    private ResponseCookie createCookie(String token) {
        return ResponseCookie.from("jwt_token", token)
                .httpOnly(true)    // JavaScript 접근 차단
                .secure(true)      // HTTPS 전용
                .path("/")         // 모든 경로에서 유효
                .maxAge(60 * 60 * 24) // 1일(86400초)
                .sameSite("Lax")   // CSRF 방지
                .domain("localhost") // 개발 환경 도메인
                .build();
    }

}
