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
public class ExhibitionTicketPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "seat", nullable = false, length = 50)
  private String seat;

  @Column(name = "price", nullable = false)
  private String price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exhibition_id")
  private Exhibition exhibition;
}
