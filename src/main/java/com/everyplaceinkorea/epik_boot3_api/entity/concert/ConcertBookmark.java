package com.everyplaceinkorea.epik_boot3_api.entity.concert;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "concert_bookmark")
public class ConcertBookmark {

  @EmbeddedId // 복합키
  private ConcertBookmarkId id;

  @ManyToOne
  @MapsId("concertId") // 외래키로 매핑
  @JoinColumn(name = "concert_id")
  private Concert concert;

  @ManyToOne
  @MapsId("memberId") // 외래키로 매핑
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
