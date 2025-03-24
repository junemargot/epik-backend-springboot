package com.everyplaceinkorea.epik_boot3_api.repository.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketPrice;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionTicketPriceRepository extends JpaRepository<ExhibitionTicketPrice, Long> {

  List<ExhibitionTicketPrice> findAllByExhibitionId(Long id);
}
