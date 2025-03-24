package com.everyplaceinkorea.epik_boot3_api.member.feed.sesrvice;

import com.everyplaceinkorea.epik_boot3_api.EditorImage.UploadFolderType;
import com.everyplaceinkorea.epik_boot3_api.entity.feed.*;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.member.feed.dto.FeedCreateDto;
import com.everyplaceinkorea.epik_boot3_api.member.feed.dto.FeedUpdateDto;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedCategoryRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedImageRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedLikeRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFeedService implements FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;
    private final FeedCategoryRepository feedCategoryRepository;
    private final MemberRepository memberRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final ModelMapper modelMapper;

    @Value("${file.tmp-dir}")
    private String tmpPath;

    @Value("${file.upload-dir}")
    private String uploadPath;

    @Transactional
    @Override
    public Long create(FeedCreateDto feedCreateDto, MultipartFile[] files) {
        Member member = memberRepository.findById(1L).orElseThrow();
        FeedCategory feedCategory = feedCategoryRepository.findById(1L).orElseThrow();
        // 피드 저장하기
        Feed feed = modelMapper.map(feedCreateDto, Feed.class);
        feed.setMember(member);
        feed.setCategory(feedCategory);

        Feed savedFeed = feedRepository.save(feed);

        // 사진이 넘어오니깐 이걸 feed_image테이블에 저장하기
        if (files.length > 0) {
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "") + extension;

                File folder = new File(System.getProperty("user.dir") + "/" + uploadPath + "/" + UploadFolderType.FEED.getFolderName());

                if (!folder.exists()) {
                    if (!folder.mkdirs()) {
                        throw new IllegalArgumentException("이미지 저장 폴더 생성에 실패 하였습니다.");
                    }
                }

                String fullPath = folder.getAbsolutePath() + "/" + savedFileName;
                try {
                    file.transferTo(new File(fullPath));
                } catch (IOException e) {
                    log.info(e.getMessage());
                }

                FeedImage feedImage = FeedImage.builder()
                        .imageSaveName(savedFileName)
                        .feed(savedFeed)
                        .build();

                feedImageRepository.save(feedImage);

            }
        }

        return savedFeed.getId();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Feed feed = feedRepository.findById(id).orElseThrow();
        feed.delete();
    }

    @Transactional
    @Override
    public void update(Long id, FeedUpdateDto feedUpdateDto) {
        Feed feed = feedRepository.findById(id).orElseThrow();
        FeedCategory feedCategory = feedCategoryRepository.findById(feedUpdateDto.getCategoryId()).orElseThrow();
        feed.update(feedUpdateDto.getContent(), feedCategory);
    }

    @Transactional
    @Override
    public void likeFeed(Long postId) {
        // 피드 번호와 회원번호로 좋아요 테이블에 데이터를 기록하고
        // 피드의 좋아요 수 up
        // ** 일단 회원번호는 임의로 부여
        Member member = memberRepository.findById(1L).orElseThrow(); //
        Feed feed = feedRepository.findById(postId).orElseThrow(); // 좋아요 증가 및 좋아요 데이터 넣기위해

        // 좋아요 테이블에 존재하는지 부터 확인하는 쿼리가 필요하네
        // 그래서 존재하지않는다면(false) 새 레코드 추가
        boolean isLiked = feedLikeRepository.existsByFeedIdAndMemberId(postId, 1L);
        if (!isLiked) {
            FeedLike feedLike = FeedLike.builder()
                    .feedId(feed.getId())
                    .memberId(member.getId())
                    .build();
            feedLikeRepository.save(feedLike);
            feed.likeCountUp();
        } else {
            FeedLike feedLike = feedLikeRepository.findByFeedIdAndMemberId(postId, 1L);
            feedLike.changeIsActive();
            feed.likeCountUp();
        }
    }

    @Transactional
    @Override
    public void unLikeFeed(Long postId) {
        Feed feed = feedRepository.findById(postId).orElseThrow();

        // 현재 피드 좋아요 테이블에 존재한다는거니깐
        FeedLike feedLike = feedLikeRepository.findByFeedIdAndMemberId(postId, 1L);
        feedLike.changeIsActive();
        feed.likeCountDown();
    }
}
