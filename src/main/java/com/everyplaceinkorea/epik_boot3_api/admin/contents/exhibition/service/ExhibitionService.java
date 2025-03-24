package com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.service;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto.ConcertListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto.ConcertRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExhibitionService {

  // 전체 조회(R)
//  ExhibitionListDto getList();
  ExhibitionListDto getList(int page, String keyword, String searchType);

  // 상세(R)
  ExhibitionResponseDto getById(Long id);

  // 등록(C)
  ExhibitionResponseDto create(ExhibitionRequestDto exhibitionRequestDto, MultipartFile file) throws IOException;

  // 수정(U)
  ExhibitionResponseDto update(Long id, ExhibitionRequestDto exhibitionRequestDto, MultipartFile file);

  // 삭제(D)
  void delete(Long id);

  // 검색


  // 상태 변경
  void updateExhibitionStatus(Long id);
}
