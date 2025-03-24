package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalBookmark;
import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalBookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicalBookmarkRepository extends JpaRepository<MusicalBookmark, MusicalBookmarkId> {
    @Query("SELECT mb FROM MusicalBookmark mb WHERE mb.member.id = :memberId AND mb.isActive = true")
    List<MusicalBookmark> findMusicalBookmarksByMemberId(@Param("memberId") Long memberId);

}
