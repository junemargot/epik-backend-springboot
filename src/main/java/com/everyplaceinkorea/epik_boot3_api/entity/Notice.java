package com.everyplaceinkorea.epik_boot3_api.entity;

import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice")
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "write_date")
  @CurrentTimestamp
  private LocalDateTime writeDate;

  @Column(name = "view_count")
  private Integer viewCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

}
