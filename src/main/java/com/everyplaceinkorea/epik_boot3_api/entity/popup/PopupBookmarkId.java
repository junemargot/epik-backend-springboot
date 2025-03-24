package com.everyplaceinkorea.epik_boot3_api.entity.popup;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PopupBookmarkId implements Serializable {

  private Long popupId;
  private Long memberId;

}
