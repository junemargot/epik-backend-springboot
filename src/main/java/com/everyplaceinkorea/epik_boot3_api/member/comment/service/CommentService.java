package com.everyplaceinkorea.epik_boot3_api.member.comment.service;

import com.everyplaceinkorea.epik_boot3_api.member.comment.dto.CommentCreateDto;
import com.everyplaceinkorea.epik_boot3_api.member.comment.dto.CommentResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.comment.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    // 댓글 등록
    Long create(Long feedId, Long memberId, CommentCreateDto commentCreateDto);
    // 댓글 조회
    List<CommentResponseDto> getList(Long feedId);
    // 댓글 수정
    void update(Long commentId, CommentUpdateDto updateDto);

    void delete(Long commentId);
}
