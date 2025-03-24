package com.everyplaceinkorea.epik_boot3_api.member.exhibition.service;

import com.everyplaceinkorea.epik_boot3_api.member.exhibition.dto.ExhibitionResponseDto;

import java.util.List;

public interface ExhibitionService {
    // 북마크
    List<ExhibitionResponseDto> getBookmark(Long id);
}
