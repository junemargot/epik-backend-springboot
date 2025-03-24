package com.everyplaceinkorea.epik_boot3_api.entity.concert;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "concert_ticket_office")
public class ConcertTicketOffice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "link", nullable = false)
  private String link;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_id")
  private Concert concert;
}
