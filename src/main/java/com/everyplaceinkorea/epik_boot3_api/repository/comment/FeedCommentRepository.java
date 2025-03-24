package com.everyplaceinkorea.epik_boot3_api.repository.comment;

import com.everyplaceinkorea.epik_boot3_api.entity.comment.FeedComment;
import com.everyplaceinkorea.epik_boot3_api.member.comment.dto.CommentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    List<FeedComment> findAllByFeedId(Long feedId);
}
