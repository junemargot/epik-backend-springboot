package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConcertTicketPriceRepository extends JpaRepository<ConcertTicketPrice, Long> {

  List<ConcertTicketPrice> findAllByConcertId(Long id);

  @Modifying
  @Transactional
  @Query("DELETE FROM ConcertTicketPrice c WHERE c.concert.id = :concertId")
  void deleteAllByConcertId(@Param("concertId") Long id);
}
