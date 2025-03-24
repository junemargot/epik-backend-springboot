package com.everyplaceinkorea.epik_boot3_api.anonymous.feed.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.feed.dto.FeedCommentDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.feed.dto.FeedResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.comment.FeedComment;
import com.everyplaceinkorea.epik_boot3_api.entity.feed.Feed;
import com.everyplaceinkorea.epik_boot3_api.entity.feed.FeedImage;
import com.everyplaceinkorea.epik_boot3_api.repository.comment.FeedCommentRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedImageRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultFeedService implements FeedService {

  private final FeedRepository feedRepository;
  private final FeedImageRepository feedImageRepository;
  private final FeedCommentRepository feedCommentRepository;

  @Override
  public List<FeedResponseDto> getFeeds(Long lastId) {
    // 자 이제 lastId를 사용해 리스트 뽑아오기
    Pageable pageable = PageRequest.of(0, 15, Sort.by("id").ascending());
    List<Feed> feeds = feedRepository.findFeedsByLastId(lastId, pageable);
    // feedid를 통해 아까처럼 불러오면 되겠다.
    List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
    List<FeedCommentDto> commentDtos = new ArrayList<>();

    // 여기서 feed하나씩 꺼내서 그 번호로 다른 테이블에 접근한다.
    for (Feed feed : feeds) {
      Long feedId = feed.getId(); // feedId
      FeedResponseDto responseDto = FeedResponseDto.builder()
              .writer(feed.getMember().getNickname())
              .writeDate(feed.getWriteDate())
              .likeCount(feed.getLikeCount())
              .commentCount(feed.getCommentCount())
              .content(feed.getContent())
              .build();

      // 현재 번호의 피드의 댓글 테이블의 레코드가 필요
      List<FeedComment> allComnets = feedCommentRepository.findAllByFeedId(feedId);
      for (FeedComment feedComment : allComnets) {
        FeedCommentDto comment = FeedCommentDto.builder()
                .writer(feedComment.getMember().getNickname())
                .writeDate(feedComment.getWriteDate())
                .content(feedComment.getContent())
                .build();
        commentDtos.add(comment);
      }

      responseDto.setComments(commentDtos);


      // 이미지 테이블
      List<FeedImage> feedImages = feedImageRepository.findAllByFeedId(feedId);
      String[] imageSaveName = new String[feedImages.size()];
      int index = 0; // 배열 인덱스

      for (FeedImage feedImage : feedImages) {
        imageSaveName[index++] = feedImage.getImageSaveName(); // 각 인덱스에 값 저장
      }

      //이제 이걸 dto에 담자.
      responseDto.setImageSaveName(imageSaveName);

      feedResponseDtos.add(responseDto);
    }


    return feedResponseDtos;
  }

  @Override
  public List<FeedResponseDto> getByCategories(Long categoryId, Long lastId) {
    Pageable pageable = PageRequest.of(0, 15, Sort.by("id").ascending());
    List<Feed> feeds = feedRepository.findAllByCategoryId(categoryId);

    List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
    List<FeedCommentDto> commentDtos = new ArrayList<>();
// 여기서 feed하나씩 꺼내서 그 번호로 다른 테이블에 접근한다.
    for (Feed feed : feeds) {
      Long feedId = feed.getId(); // feedId
      FeedResponseDto responseDto = FeedResponseDto.builder()
              .writer(feed.getMember().getNickname())
              .writeDate(feed.getWriteDate())
              .likeCount(feed.getLikeCount())
              .commentCount(feed.getCommentCount())
              .content(feed.getContent())
              .build();

      // 현재 번호의 피드의 댓글 테이블의 레코드가 필요
      List<FeedComment> allComnets = feedCommentRepository.findAllByFeedId(feedId);
      for (FeedComment feedComment : allComnets) {
        FeedCommentDto comment = FeedCommentDto.builder()
                .writer(feedComment.getMember().getNickname())
                .writeDate(feedComment.getWriteDate())
                .content(feedComment.getContent())
                .build();
        commentDtos.add(comment);
      }

      responseDto.setComments(commentDtos);


      // 이미지 테이블
      List<FeedImage> feedImages = feedImageRepository.findAllByFeedId(feedId);
      String[] imageSaveName = new String[feedImages.size()];
      int index = 0; // 배열 인덱스

      for (FeedImage feedImage : feedImages) {
        imageSaveName[index++] = feedImage.getImageSaveName(); // 각 인덱스에 값 저장
      }

      //이제 이걸 dto에 담자.
      responseDto.setImageSaveName(imageSaveName);

      feedResponseDtos.add(responseDto);
    }


    return feedResponseDtos;
  }
}
