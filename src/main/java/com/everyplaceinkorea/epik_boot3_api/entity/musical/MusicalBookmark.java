package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "musical_bookmark")
public class MusicalBookmark {

  @EmbeddedId // 복합키
  private MusicalBookmarkId id;

  @ManyToOne
  @MapsId("musicalId") // 외래키로 매핑
  @JoinColumn(name = "musical_id")
  private Musical musical;

  @ManyToOne
  @MapsId("memberId") // 외래키로 매핑
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
