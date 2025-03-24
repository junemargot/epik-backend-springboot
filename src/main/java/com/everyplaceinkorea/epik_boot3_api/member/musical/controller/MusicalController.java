package com.everyplaceinkorea.epik_boot3_api.member.musical.controller;

import com.everyplaceinkorea.epik_boot3_api.member.musical.dto.MusicalResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.musical.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/musical")
@RequiredArgsConstructor
public class MusicalController {

    private final MusicalService musicalService;

    // 북마크 조회
    // 북마크
    @GetMapping("{id}/bookmark")
    public ResponseEntity<List<MusicalResponseDto>> getBookmark(@PathVariable Long id) {

        List<MusicalResponseDto> responseDtos = musicalService.getBookmark(id);
        return ResponseEntity.ok(responseDtos);
    }
}
