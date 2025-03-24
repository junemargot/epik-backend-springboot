package com.everyplaceinkorea.epik_boot3_api.member.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDto {
    private String id;
    private String content;
}
