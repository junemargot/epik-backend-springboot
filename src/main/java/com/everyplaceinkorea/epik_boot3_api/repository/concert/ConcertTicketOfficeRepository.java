package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertTicketOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertTicketOfficeRepository extends JpaRepository<ConcertTicketOffice, Long> {

  List<ConcertTicketOffice> findAllByConcertId(Long id);
}
