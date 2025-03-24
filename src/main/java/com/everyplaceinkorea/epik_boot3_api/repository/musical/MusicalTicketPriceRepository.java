package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicalTicketPriceRepository extends JpaRepository<MusicalTicketPrice, Long> {
    List<MusicalTicketPrice> findAllByMusicalId(Long id);
}
