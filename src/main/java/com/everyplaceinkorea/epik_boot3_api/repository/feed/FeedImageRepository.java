package com.everyplaceinkorea.epik_boot3_api.repository.feed;

import com.everyplaceinkorea.epik_boot3_api.entity.feed.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
  List<FeedImage> findAllByFeedId(Long feedId);
}
