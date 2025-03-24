package com.everyplaceinkorea.epik_boot3_api.admin.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSearchDto {

  private Integer page;
  private String keyword;
  private String searchType;

}
