package com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MusicalTicketPriceDto {
    private Long id;
    @NotBlank
    private String seat;
    @NotNull
    private String price;
    @NotNull
    private Long musicalId;
}
