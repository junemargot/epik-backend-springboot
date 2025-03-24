package com.everyplaceinkorea.epik_boot3_api.entity.concert;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "concert_ticket_price")
public class ConcertTicketPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "seat", nullable = false, length = 50)
  private String seat;

  @Column(name = "price", nullable = false)
  private String price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_id")
  private Concert concert;
}
