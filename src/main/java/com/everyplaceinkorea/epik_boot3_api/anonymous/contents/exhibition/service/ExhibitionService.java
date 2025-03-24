package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.dto.ExhibitionResponseDto;

import java.util.List;

public interface ExhibitionService {
    List<ExhibitionResponseDto> getExhibitionsByRegion(Long regionId, Integer page);
    List<ExhibitionResponseDto> getExhibitionsByRandom();
}
