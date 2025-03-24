package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.controller;


import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.dto.PopupResponseDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.service.PopupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("popup")
@RequiredArgsConstructor
@Slf4j
public class PopupController {

    private final PopupService popupService;

    //이주의 신규 팝업 - weeklyPopup
    // api/v1/popup/weekly
    //api/v1/popup/category?category=1&startDate=2024-11-18&page=1
    @GetMapping("category")
    public ResponseEntity<List<PopupResponseDto>> getPopupsFindCategoryAndStartDate(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") Integer page) {

        return ResponseEntity.status(OK)
                .body(popupService.getPopupsFindCategoryAndStartDate(categoryId, page));

    }

    // /api/v1/popup/region?regionId=1
    @GetMapping("region")
    public ResponseEntity<List<PopupResponseDto>> getPopupsFindByRegionAndStartDate(
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "1") Integer page) {

        return ResponseEntity.status(OK)
                .body(popupService.getPopupsFindByRegionAndStartDate(regionId, page));

    }

    //카테고리 선택 후 지역 선택
    @GetMapping("category/region")
    public ResponseEntity<List<PopupResponseDto>> getPopupsFindByCategoryAndRegionAndStartDate(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "1") Integer page) {

        log.info("categoryId ={}", categoryId);

        return ResponseEntity.status(OK)
                .body(popupService.getPopupsFindByCategoryAndRegionAndStartDate(categoryId, regionId, page));
    }

    // 랜덤 이미지 조회
    ///api/v1/popup/category/random
    @GetMapping("random")
    public ResponseEntity<List<PopupResponseDto>> getPopupsByRandom() {
        return ResponseEntity.status(OK)
                .body(popupService.getPopupsByRandom());
    }

    @GetMapping("{id}")
    public ResponseEntity<PopupResponseDto> getPopup(@PathVariable Long id) {
        log.info("상세조회 요청 호출완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(popupService.getPopup(id));
    }
}
