package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.Notice;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.musical.Musical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

  @Query("SELECT c FROM Concert c WHERE " +
          "(:searchType IS NULL OR " +
          "(:searchType = 'ALL' AND (c.title LIKE %:keyword% OR c.content LIKE %:keyword% OR c.member.nickname LIKE %:keyword%)) OR " +
          "(:searchType = 'TITLE' AND c.title LIKE %:keyword%) OR " +
          "(:searchType = 'CONTENT' AND c.content LIKE %:keyword%) OR " +
          "(:searchType = 'WRITER' AND c.member.nickname LIKE %:keyword%)) AND " +
          "(:keyword IS NULL OR " +
          "(:searchType = 'ALL' AND (c.title LIKE %:keyword% OR c.content LIKE %:keyword% OR c.member.nickname LIKE %:keyword%)) OR " +
          "(:searchType = 'TITLE' AND c.title LIKE %:keyword%) OR " +
          "(:searchType = 'CONTENT' AND c.content LIKE %:keyword%) OR " +
          "(:searchType = 'WRITER' AND c.member.nickname LIKE %:keyword%))")
  Page<Concert> searchConcert(@Param("keyword") String keyword,
                              @Param("searchType") String searchType,
                              Pageable pageable);

  @Query("SELECT c FROM Concert c WHERE (:regionId IS NULL OR c.region.id = :regionId) AND c.endDate >= :endDate AND c.status = 'ACTIVE'")
  Page<Concert> findConcertsByRegion(@Param("regionId") Long regionId, @Param("endDate") LocalDate endDate, Pageable pageable);

  // 랜덤이미지조회
  @Query(value = "SELECT c FROM Concert c ORDER BY RAND() LIMIT 10")
  List<Concert> findConcertByRandom();


}
