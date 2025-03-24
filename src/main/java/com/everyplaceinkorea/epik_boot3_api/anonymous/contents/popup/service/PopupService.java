package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.dto.PopupResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface PopupService {
    List<PopupResponseDto> getPopupsFindCategoryAndStartDate(Long categoryId, Integer page);
    List<PopupResponseDto> getPopupsFindByRegionAndStartDate(Long regionId, Integer page);
    List<PopupResponseDto> getPopupsFindByCategoryAndRegionAndStartDate(Long categoryId, Long regionId, Integer page);
    List<PopupResponseDto> getPopupsByRandom();
    PopupResponseDto getPopup(Long id);
}
