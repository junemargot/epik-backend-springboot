package com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.controller;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionListDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto.ExhibitionResponseDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("admin/exhibition")
@RequiredArgsConstructor
@Slf4j
public class ExhibitionController {

  private final ExhibitionService exhibitionService;

//  @GetMapping
//  public ResponseEntity<ConcertListDto> getAllConcerts() {
//
//    ConcertListDto concertList = concertService.getList();
//
//    return ResponseEntity.ok(concertList);
//  }

  @GetMapping
  public ResponseEntity<ExhibitionListDto> getAllExhibitions(
          @RequestParam(name = "p", defaultValue = "1") Integer page,
          @RequestParam(name = "k", required = false) String keyword,
          @RequestParam(name = "s", required = false) String searchType) {

    // ConcertService에서 검색 및 페이징 데이터를 가져옴
    ExhibitionListDto exhibitions = exhibitionService.getList(page, keyword, searchType);

    // 가져온 데이터를 ResponseEntity로 반환
    return ResponseEntity.ok().body(exhibitions);
  }


  @GetMapping("/{id}")
  public ResponseEntity<ExhibitionResponseDto> getExhibitionById(@PathVariable Long id) {

    ExhibitionResponseDto exhibition = exhibitionService.getById(id);
    return ResponseEntity.ok(exhibition);
  }

  // 콘서트 등록 요청
  @PostMapping
  public ResponseEntity<ExhibitionResponseDto> createExhibition(@ModelAttribute ExhibitionRequestDto exhibitionRequestDto,
                                                                MultipartFile files) throws IOException {
    log.info("Exhibition request: {}", exhibitionRequestDto.toString());
    return ResponseEntity.status(HttpStatus.OK)
            .body(exhibitionService.create(exhibitionRequestDto, files));

  }

}
