package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExhibitionImageRepository extends JpaRepository<ExhibitionImage, Long> {

  @Modifying
  @Transactional
  @Query("DELETE FROM ExhibitionImage ei WHERE ei.exhibition.id = :exhibitionId")
  void deleteAllByExhibitionId(@Param("exhibitionId") Long id);
}
