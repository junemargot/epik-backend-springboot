package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MusicalImageRepository extends JpaRepository<MusicalImage, Long> {

  @Modifying
  @Transactional
  @Query("DELETE FROM MusicalImage mi WHERE mi.musical.id = :musicalId")
  void deleteAllByMusicalId(@Param("musicalId") Long id);
}

