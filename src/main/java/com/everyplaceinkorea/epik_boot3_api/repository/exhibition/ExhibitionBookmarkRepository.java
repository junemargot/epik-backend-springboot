package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionBookmark;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionBookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExhibitionBookmarkRepository extends JpaRepository<ExhibitionBookmark, ExhibitionBookmarkId> {
    @Query("SELECT cb FROM ExhibitionBookmark cb WHERE cb.member.id = :memberId AND cb.isActive = true")
    List<ExhibitionBookmark> findExhibitionBookmarksByMemberId(@Param("memberId") Long memberId);

}
