package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.service;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PopupService {
    // 등록
    Long create(PopupRequestDto popupRequestDto, MultipartFile[] files) throws IOException;
    // 전체 조회 및 검색
    PopupListDto getList(int page, String keyword, String searchType);
    // 상세 조회
    PopupResponseDto getPopup(Long id);
    // 수정
    void update(Long id, PopupRequestDto popupRequestDto);
    // 삭제
    void delete(Long id);
    // 비공개 상태 변경
    void updatePopupStatus(Long id);


}
