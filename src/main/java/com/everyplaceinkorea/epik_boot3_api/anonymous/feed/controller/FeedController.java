package com.everyplaceinkorea.epik_boot3_api.anonymous.feed.controller;
/*
 * 방문자 피드 요청 api
 * */

import com.everyplaceinkorea.epik_boot3_api.anonymous.feed.dto.FeedResponseDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("feed")
@RequiredArgsConstructor
public class FeedController {

  private final FeedService feedService;

  // 피드 전체 조회 = 메인
  @GetMapping()
  public ResponseEntity<List<FeedResponseDto>> getFeeds(@RequestParam(required = false) Long lastId) {
    // 더보기버튼은 데이터의 마지막 id를 보내줘서 해야겠네 으흠.
    // 이러면 지역별 더보기버튼을 바꿔야겠네
    return ResponseEntity.status(HttpStatus.OK)
            .body(feedService.getFeeds(lastId));
  }

  // 피드 카테고리별 조회
  @GetMapping("category/{categoryId}")
  public ResponseEntity<List<FeedResponseDto>> getByCategories(@PathVariable Long categoryId,
                                                               @RequestParam(required = false) Long lastId) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(feedService.getByCategories(categoryId, lastId));  // categoryId를 전달해야 합니다
  }

  // 피드 검색
//    @GetMapping("search")

}
