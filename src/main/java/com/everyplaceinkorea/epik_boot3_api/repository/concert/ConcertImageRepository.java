package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ConcertImageRepository extends JpaRepository<ConcertImage, Long> {

  @Modifying
  @Transactional
  @Query("DELETE FROM ConcertImage ci WHERE ci.concert.id = :concertId")
  void deleteAllByConcertId(@Param("concertId") Long id);
}
