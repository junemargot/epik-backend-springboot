package com.everyplaceinkorea.epik_boot3_api.entity.feed;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(FeedLikeId.class)
@Getter
public class FeedLike {

    @Id
    private Long feedId;

    @Id
    private Long memberId;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive = true;

    @Builder
    public FeedLike(Long feedId, Long memberId) {
        this.feedId = feedId;
        this.memberId = memberId;
    }

    public void changeIsActive() {
        this.isActive = !this.isActive;
    }
}
