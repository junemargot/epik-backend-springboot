package com.everyplaceinkorea.epik_boot3_api.repository.feed;

import com.everyplaceinkorea.epik_boot3_api.entity.feed.FeedLike;
import com.everyplaceinkorea.epik_boot3_api.entity.feed.FeedLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeId> {
    boolean existsByFeedIdAndMemberId(Long feedId, Long memberId);
    // feedId와 memberId로 레코드 한 개 조회
    FeedLike findByFeedIdAndMemberId(Long feedId, Long memberId);
}
