package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.controller;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.service.PopupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("admin/popup")
@RequiredArgsConstructor
@Slf4j
public class PopupController {

    private final PopupService popupService;

    // 팝업 등록(C)
    // api/v1/admin/popup
    @PostMapping/*(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})*/
    public ResponseEntity<Long> create(PopupRequestDto popupRequestDto,
                                                   MultipartFile[] files) throws IOException {

        log.info("popupRequestDto : {}", popupRequestDto);
        // 파일 리스트 순회
        for (MultipartFile file : files) {
            // 파일명 출력
            String fileName = file.getOriginalFilename();
            log.info("fileName : {}", fileName);
        }
        return ResponseEntity.status(OK)
                .body(popupService.create(popupRequestDto, files));
    }

    // 팝업 전체조회 및 검색(R)
    // api/v1/admin/popup
    @GetMapping()
    public ResponseEntity<PopupListDto> getList(
            @RequestParam(defaultValue = "1") int page, //페이지 번호
            @RequestParam(required = false) String keyword,//검색어
            @RequestParam(required = false) String searchType) { //검색 필터링
        return ResponseEntity.status(OK)
                .body(popupService.getList(page, keyword, searchType));
    }

    //팝업 상세조회(R)
    @GetMapping("{id}")
    public ResponseEntity<PopupResponseDto> getPopup(@PathVariable Long id) {
        log.info("상세조회 요청 호출완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(popupService.getPopup(id));
    }

    // 팝업 수정(U)
    // api/v1/admin/popup/{id}
    @PatchMapping("{id}")
    public ResponseEntity<PopupListDto> update(
            @PathVariable Long id,
            @RequestPart(name = "request") PopupRequestDto popupRequestDto,
            @RequestPart MultipartFile file) {
        popupService.update(id, popupRequestDto);
        return ResponseEntity.noContent().build();
    }

    // 팝업 삭제(D)
    // api/v1/admin/popup/{id}
    @DeleteMapping("{id}")
    public ResponseEntity <Void> delete(@PathVariable Long id) {
        popupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 팝업 비공개/공개 상태 변경
    // api/v1/admin/popup/{id}/status
    @PatchMapping("{id}/status")
    public ResponseEntity <Void> changeMusicalStatus(@PathVariable Long id) {
        popupService.updatePopupStatus(id);
        return ResponseEntity.noContent().build();
    }
}

