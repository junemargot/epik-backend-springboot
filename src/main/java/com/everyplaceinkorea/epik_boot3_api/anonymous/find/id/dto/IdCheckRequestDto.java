package com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdCheckRequestDto {
    String email;
}
