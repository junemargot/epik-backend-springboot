package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertBookmark;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertBookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertBookmarkRepository extends JpaRepository<ConcertBookmark, ConcertBookmarkId> {
    @Query("SELECT cb FROM ConcertBookmark cb WHERE cb.member.id = :memberId AND cb.isActive = true")
    List<ConcertBookmark> findConcertBookmarksByMemberId(@Param("memberId") Long memberId);

}
