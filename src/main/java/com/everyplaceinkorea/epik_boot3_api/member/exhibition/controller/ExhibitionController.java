package com.everyplaceinkorea.epik_boot3_api.member.exhibition.controller;

import com.everyplaceinkorea.epik_boot3_api.member.exhibition.dto.ExhibitionResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/exhibition")
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    // 북마크 조회
    // 북마크
    @GetMapping("{id}/bookmark")
    public ResponseEntity<List<ExhibitionResponseDto>> getBookmark(@PathVariable Long id) {

        List<ExhibitionResponseDto> responseDtos = exhibitionService.getBookmark(id);
        return ResponseEntity.ok(responseDtos);
    }
}
