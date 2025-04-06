package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicalTicketPriceRepository extends JpaRepository<MusicalTicketPrice, Long> {

    List<MusicalTicketPrice> findAllByMusicalId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM MusicalTicketPrice m WHERE m.musical.id = :musicalId")
    void deleteByMusicalId(@Param("musicalId") Long id);
}
