package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.dto.MusicalResponseDto;

import java.util.List;

public interface MusicalService {
    List<MusicalResponseDto> getMusicalsByRegion(Long regionId, Integer page);
    List<MusicalResponseDto> getMusicalsByRandom();
}
