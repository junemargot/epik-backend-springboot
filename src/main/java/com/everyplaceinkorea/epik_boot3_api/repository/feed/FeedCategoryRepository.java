package com.everyplaceinkorea.epik_boot3_api.repository.feed;

import com.everyplaceinkorea.epik_boot3_api.entity.feed.FeedCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCategoryRepository extends JpaRepository<FeedCategory, Long> {
}
