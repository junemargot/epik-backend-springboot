package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketOffice;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionTicketOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionTicketOfficeRepository extends JpaRepository<ExhibitionTicketOffice, Long> {

  List<ExhibitionTicketOffice> findAllByExhibitionId(Long id);
}
