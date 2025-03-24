package com.everyplaceinkorea.epik_boot3_api.repository.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertImageRepository extends JpaRepository<ConcertImage, Long> {
}
