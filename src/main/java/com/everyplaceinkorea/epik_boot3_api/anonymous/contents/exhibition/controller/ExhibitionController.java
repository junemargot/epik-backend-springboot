package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.controller;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.dto.ExhibitionResponseDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("exhibition")
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    // 지역 필터링 조회
    // api/v1/musical?region=1&page=1
    @GetMapping()
    public ResponseEntity<List<ExhibitionResponseDto>> getExhibitionsByRegion(@RequestParam(name = "region", required = false) Long regionId,
                                                                              @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.status(OK)
                .body(exhibitionService.getExhibitionsByRegion(regionId, page));
    }

    // 랜덤 이미지 조회
    @GetMapping("random")
    public ResponseEntity<List<ExhibitionResponseDto>> getExhibitionsByRandom() {
        return ResponseEntity.status(OK)
                .body(exhibitionService.getExhibitionsByRandom());
    }
}
