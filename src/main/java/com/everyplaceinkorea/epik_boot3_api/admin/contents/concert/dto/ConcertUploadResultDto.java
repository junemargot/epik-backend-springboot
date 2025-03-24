package com.everyplaceinkorea.epik_boot3_api.admin.contents.concert.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertUploadResultDto {
  private String fileSavedName; // 저장된 파일명
  private String filePath; // 파일 저장경로
}
