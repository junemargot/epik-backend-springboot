package com.everyplaceinkorea.epik_boot3_api.repository.Member;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);

    @Query("SELECT m FROM Member m WHERE " +
            "(:searchType IS NULL OR " +
            "(:searchType = 'ALL' AND (m.username LIKE %:keyword% OR m.nickname LIKE %:keyword%)) OR " +
            "(:searchType = 'USERNAME_NICKNAME' AND (m.username LIKE %:keyword% OR m.nickname LIKE %:keyword%)) OR " +
            "(:searchType = 'USERNAME' AND m.username LIKE %:keyword%) OR " +
            "(:searchType = 'NICKNAME' AND m.nickname LIKE %:keyword%)) AND " +
            "(:keyword IS NULL OR " +
            "(:searchType = 'ALL' AND (m.username LIKE %:keyword% OR m.nickname LIKE %:keyword%)) OR " +
            "(:searchType = 'USERNAME_NICKNAME' AND (m.username LIKE %:keyword% OR m.nickname LIKE %:keyword%)) OR " +
            "(:searchType = 'USERNAME' AND m.username LIKE %:keyword%) OR " +
            "(:searchType = 'NICKNAME' AND m.nickname LIKE %:keyword%))")
    Page<Member> searchMember(@Param("keyword") String keyword,
                              @Param("searchType") String searchType,
                              Pageable pageable);


}
