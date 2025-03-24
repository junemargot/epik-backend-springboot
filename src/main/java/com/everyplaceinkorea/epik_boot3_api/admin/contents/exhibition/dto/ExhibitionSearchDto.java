package com.everyplaceinkorea.epik_boot3_api.admin.contents.exhibition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionSearchDto {

  private Integer page;
  private String keyword;
  private String searchType;

}
