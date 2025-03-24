package com.everyplaceinkorea.epik_boot3_api.member.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedUpdateDto {
    private String content;
    private Long categoryId;

}
