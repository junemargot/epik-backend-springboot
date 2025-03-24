package com.everyplaceinkorea.epik_boot3_api.entity.popup;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.entity.musical.Musical;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "popup_bookmark")
public class PopupBookmark {

  @EmbeddedId // 복합키
  private PopupBookmarkId id;

  @ManyToOne
  @MapsId("popupId") // 외래키로 매핑
  @JoinColumn(name = "popup_id")
  private Popup popup;

  @ManyToOne
  @MapsId("memberId") // 외래키로 매핑
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
