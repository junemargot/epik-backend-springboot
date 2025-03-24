package com.everyplaceinkorea.epik_boot3_api.entity.feed;

import java.io.Serializable;
import java.util.Objects;

public class FeedLikeId implements Serializable {

    private Long feedId;
    private Long memberId;

    // 기본 생성자
    public FeedLikeId() {}

    // 생성자
    public FeedLikeId(Long feedId, Long memberId) {
        this.feedId = feedId;
        this.memberId = memberId;
    }

    // equals와 hashCode 구현 (중요)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedLikeId that = (FeedLikeId) o;
        return Objects.equals(feedId, that.feedId) &&
                Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, memberId);
    }
}
