package com.everyplaceinkorea.epik_boot3_api.member.concert.controller;

import com.everyplaceinkorea.epik_boot3_api.member.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/concert")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    // 북마크 조회
    // 북마크
    @GetMapping("{id}/bookmark")
    public ResponseEntity<List<ConcertResponseDto>> getBookmark(@PathVariable Long id) {

        List<ConcertResponseDto> responseDtos = concertService.getBookmark(id);
        return ResponseEntity.ok(responseDtos);
    }
}
