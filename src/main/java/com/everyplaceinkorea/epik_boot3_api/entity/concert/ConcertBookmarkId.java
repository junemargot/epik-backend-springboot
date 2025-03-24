package com.everyplaceinkorea.epik_boot3_api.entity.concert;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ConcertBookmarkId implements Serializable {

  private Long concertId;
  private Long memberId;

}
