package com.everyplaceinkorea.epik_boot3_api.member.feed.sesrvice;

import com.everyplaceinkorea.epik_boot3_api.member.feed.dto.FeedCreateDto;
import com.everyplaceinkorea.epik_boot3_api.member.feed.dto.FeedUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {
    // 피드 등록
    Long create(FeedCreateDto feedCreateDto, MultipartFile[] files);
    // 피드 삭제
    void delete(Long id);


    void update(Long id, FeedUpdateDto feedUpdateDto);

    void likeFeed(Long postId);

    void unLikeFeed(Long postId);
}
