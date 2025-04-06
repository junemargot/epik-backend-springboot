package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketPrice;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExhibitionTicketPriceRepository extends JpaRepository<ExhibitionTicketPrice, Long> {

  List<ExhibitionTicketPrice> findAllByExhibitionId(Long id);

  @Modifying
  @Transactional
  @Query("DELETE FROM ExhibitionTicketPrice e WHERE e.exhibition.id = :id")
  void deleteByExhibitionId(Long id);
}
