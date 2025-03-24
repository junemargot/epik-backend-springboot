package com.everyplaceinkorea.epik_boot3_api.member.concert.service;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertBookmark;
import com.everyplaceinkorea.epik_boot3_api.member.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.repository.concert.ConcertBookmarkRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultConcertService implements ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertBookmarkRepository concertBookmarkRepository;

    @Override
    public List<ConcertResponseDto> getBookmark(Long id) {
        List<ConcertBookmark> bookmarks = concertBookmarkRepository.findConcertBookmarksByMemberId(id);
        List<Concert> concerts = bookmarks.stream()
                .map(ConcertBookmark::getConcert)
                .collect(Collectors.toList());

        List<ConcertResponseDto> responseDtos = new ArrayList<>();

                concerts.forEach(concert -> {
            Long concertId = concert.getId();
            Concert findConcert = concertRepository.findById(concertId).orElseThrow();
            ConcertResponseDto responseDto = ConcertResponseDto.builder()
                    .id(findConcert.getId())
                    .title(findConcert.getTitle())
                    .startDate(findConcert.getStartDate())
                    .endDate(findConcert.getEndDate())
                    .venue(findConcert.getVenue())
                    .saveImageName(findConcert.getFileSavedName())
                    .build();

            responseDtos.add(responseDto);

        });

        return responseDtos;
    }


}
