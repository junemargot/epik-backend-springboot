package com.everyplaceinkorea.epik_boot3_api.entity.member;

import com.everyplaceinkorea.epik_boot3_api.admin.member.LoginType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true) // loginId
    private String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "join_date", nullable = false)
    @ColumnDefault("current_timestamp()")
    private LocalDate joinDate;

    @Column(name = "type", nullable = false) //
    private Byte type;

    //nullable 소셜 로그인 추가 성공 후 false로 변경 필요
    @Enumerated(EnumType.STRING) // DB에 "ID", "NAVER".. 문자열로 저장됨
    @Column(name = "login_type", nullable = true)
    private LoginType loginType;

    @Column(name = "role", nullable = false)
    private String role;

    //nullable 방문 기록 추가 성공 후 false로 변경 필요
    @Column(name = "last_access", nullable = true)
    @ColumnDefault("current_timestamp()")
    private LocalDateTime lastAccess;

    @Column(name = "profile_img", nullable = true)
    private String profileImg;

    @Column(name = "profile_text", nullable = true)
    private String profileText;
}

