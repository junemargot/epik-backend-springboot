package com.everyplaceinkorea.epik_boot3_api.entity.feed;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feed_report")
public class FeedReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "date", nullable = false)
  @CurrentTimestamp
  private LocalDate date;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "status")
  private Byte status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reason_id")
  private FeedReport feedReport;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feed_id")
  private Feed feed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

}
