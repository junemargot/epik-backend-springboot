package com.everyplaceinkorea.epik_boot3_api.member.popup.controller;

import com.everyplaceinkorea.epik_boot3_api.member.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.concert.service.ConcertService;
import com.everyplaceinkorea.epik_boot3_api.member.popup.dto.PopupResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.popup.service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/popup")
@RequiredArgsConstructor
public class PopupController {

    private final PopupService popupService;

    // 북마크 조회
    // 북마크
    @GetMapping("{id}/bookmark")
    public ResponseEntity<List<PopupResponseDto>> getBookmark(@PathVariable Long id) {

        List<PopupResponseDto> responseDtos = popupService.getBookmark(id);
        return ResponseEntity.ok(responseDtos);
    }
}
