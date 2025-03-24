package com.everyplaceinkorea.epik_boot3_api.member.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    // 번호가 필요하네
    private Long id;
    // 작성자
    private String writer;
    // 등록일이 필요하네
    private LocalDateTime writeDate;
    // 내용
    private String content;

}
