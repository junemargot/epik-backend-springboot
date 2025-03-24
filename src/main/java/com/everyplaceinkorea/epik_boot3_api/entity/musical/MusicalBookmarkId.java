package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class MusicalBookmarkId implements Serializable {

  private Long musicalId;
  private Long memberId;

}
