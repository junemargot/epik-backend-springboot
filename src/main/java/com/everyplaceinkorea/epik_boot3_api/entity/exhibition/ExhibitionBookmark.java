package com.everyplaceinkorea.epik_boot3_api.entity.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertBookmarkId;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exhibition_bookmark")
public class ExhibitionBookmark {

  @EmbeddedId
  private ExhibitionBookmarkId id;

  @ManyToOne
  @MapsId("exhibitionId")
  @JoinColumn(name = "exhibition_id")
  private Exhibition exhibition;

  @ManyToOne
  @MapsId("memberId")
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
