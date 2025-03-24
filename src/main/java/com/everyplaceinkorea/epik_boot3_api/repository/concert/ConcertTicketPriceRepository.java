package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertTicketPriceRepository extends JpaRepository<ConcertTicketPrice, Long> {

  List<ConcertTicketPrice> findAllByConcertId(Long id);
}
