package com.everyplaceinkorea.epik_boot3_api.repository.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalTicketOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicalTicketOfficeRepository extends JpaRepository<MusicalTicketOffice, Long> {

    void deleteByMusicalId(Long musicalId);
    List<MusicalTicketOffice> findAllByMusicalId(Long id);
}
