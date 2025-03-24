package com.everyplaceinkorea.epik_boot3_api.repository;

import com.everyplaceinkorea.epik_boot3_api.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

  @Query("SELECT n FROM Notice n WHERE " +
          "(:searchType IS NULL OR " +
          "(:searchType = 'ALL' AND (n.title LIKE %:keyword% OR n.content LIKE %:keyword% OR n.member.nickname LIKE %:keyword%)) OR " +
          "(:searchType = 'TITLE' AND n.title LIKE %:keyword%) OR " +
          "(:searchType = 'CONTENT' AND n.content LIKE %:keyword%) OR " +
          "(:searchType = 'WRITER' AND n.member.nickname LIKE %:keyword%)) AND " +
          "(:keyword IS NULL OR " +
          "(:searchType = 'ALL' AND (n.title LIKE %:keyword% OR n.content LIKE %:keyword% OR n.member.nickname LIKE %:keyword%)) OR " +
          "(:searchType = 'TITLE' AND n.title LIKE %:keyword%) OR " +
          "(:searchType = 'CONTENT' AND n.content LIKE %:keyword%) OR " +
          "(:searchType = 'WRITER' AND n.member.nickname LIKE %:keyword%))")
  Page<Notice> searchNotice(Pageable pageable, @Param("keyword") String keyword, @Param("searchType") String searchType);

  };
