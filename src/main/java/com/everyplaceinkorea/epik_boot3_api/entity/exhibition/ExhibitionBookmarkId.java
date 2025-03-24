package com.everyplaceinkorea.epik_boot3_api.entity.exhibition;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ExhibitionBookmarkId implements Serializable {

  private Long exhibitionId;
  private Long memberId;

}
