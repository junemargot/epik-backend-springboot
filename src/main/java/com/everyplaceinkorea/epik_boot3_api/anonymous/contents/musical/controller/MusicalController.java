package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.controller;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.dto.MusicalResponseDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("musical")
@RequiredArgsConstructor
public class MusicalController {

    private final MusicalService musicalService;

    // 지역 필터링 조회
    // api/v1/musical?region=1&page=1
    @GetMapping()
    public ResponseEntity<List<MusicalResponseDto>> getMusicalsByRegion(@RequestParam(name = "region", required = false) Long regionId,
                                                                        @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.status(OK)
                .body(musicalService.getMusicalsByRegion(regionId, page));
    }

    // 랜덤 이미지 조회
    @GetMapping("random")
    public ResponseEntity<List<MusicalResponseDto>> getMusicalsByRandom() {
        return ResponseEntity.status(OK)
                .body(musicalService.getMusicalsByRandom());
    }

}
