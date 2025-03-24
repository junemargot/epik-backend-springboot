package com.everyplaceinkorea.epik_boot3_api.entity.exhibition;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ExhibitionTicketOffice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "link", nullable = false)
  private String link;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exhibition_id")
  private Exhibition exhibition;
}
