package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.Musical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MusicalRepository extends JpaRepository<Musical, Long> {

//    @Query("SELECT c FROM Musical c WHERE " +
//            "(:searchType = 'ALL' AND (c.title LIKE %:keyword% OR c.content LIKE %:keyword%)) OR " +
//            "(:searchType = 'TITLE' AND c.title LIKE %:keyword%) OR " +
//            "(:searchType = 'CONTENT' AND c.content LIKE %:keyword%) OR " +
//            "(:searchType = 'WRITER' AND c.member.nickname LIKE %:keyword%)")
//    Page<Musical> searchMusical(@Param("keyword") String keyword,
//                                @Param("searchType") String searchType,
//                                Pageable pageable);

    @Query("SELECT m FROM Musical m WHERE " +
            "(:searchType IS NULL OR " +
            "(:searchType = 'ALL' AND (m.title LIKE %:keyword% OR m.content LIKE %:keyword%)) OR " +
            "(:searchType = 'TITLE' AND m.title LIKE %:keyword%) OR " +
            "(:searchType = 'CONTENT' AND m.content LIKE %:keyword%) OR " +
            "(:searchType = 'WRITER' AND m.member.nickname LIKE %:keyword%)) AND " +
            "(:keyword IS NULL OR " +
            "(:searchType = 'ALL' AND (m.title LIKE %:keyword% OR m.content LIKE %:keyword%)) OR " +
            "(:searchType = 'TITLE' AND m.title LIKE %:keyword%) OR " +
            "(:searchType = 'CONTENT' AND m.content LIKE %:keyword%) OR " +
            "(:searchType = 'WRITER' AND m.member.nickname LIKE %:keyword%))")
    Page<Musical> searchMusical(@Param("keyword") String keyword,
                                @Param("searchType") String searchType,
                                Pageable pageable);

    @Query("SELECT m FROM Musical m WHERE (:regionId IS NULL OR m.region.id = :regionId) AND m.endDate >= :endDate AND m.status = 'ACTIVE'")
    Page<Musical> findMusicalsByRegion(@Param("regionId") Long regionId, @Param("endDate") LocalDate endDate, Pageable pageable);

    // 랜덤이미지조회
    @Query(value = "SELECT m FROM Musical m ORDER BY RAND() LIMIT 10")
    List<Musical> findMusicalByRandom();
}
