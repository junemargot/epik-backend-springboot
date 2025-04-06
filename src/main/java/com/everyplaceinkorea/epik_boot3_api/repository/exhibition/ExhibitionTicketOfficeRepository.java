package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketOffice;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionTicketOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExhibitionTicketOfficeRepository extends JpaRepository<ExhibitionTicketOffice, Long> {

  List<ExhibitionTicketOffice> findAllByExhibitionId(Long id);

  @Modifying
  @Transactional
  @Query("DELETE FROM ExhibitionTicketOffice e WHERE e.exhibition.id = :id")
  void deleteByExhibitionId(Long id);
}
