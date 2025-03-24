package com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.service;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalCreateDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MusicalService {
    // 등록
    MusicalResponseDto create(MusicalCreateDto musicalCreateDto, MultipartFile file) throws IOException;
    // 전체조회 및 검색
    MusicalListDto getList(int page, String keyword, String searchType);
    // 상세조회
    MusicalResponseDto getMusical(Long id);
    // 수정
    void update(Long id, MusicalUpdateDto musicalUpdateDto, MultipartFile file);
    // 삭제
    void delete(Long id);
    // 비공개 상태 변경
    void updateMusicalStatus(Long id);

}
