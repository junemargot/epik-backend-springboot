package com.everyplaceinkorea.epik_boot3_api.anonymous.feed.service;


import com.everyplaceinkorea.epik_boot3_api.anonymous.feed.dto.FeedResponseDto;

import java.util.List;

public interface FeedService {
  List<FeedResponseDto> getFeeds(Long lastId);

  List<FeedResponseDto> getByCategories(Long categoryId, Long lastId);
}
