package com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MusicalTicketOfficeDto {
    private Long id;
    private String name;
    private String link;
}
