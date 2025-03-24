package com.everyplaceinkorea.epik_boot3_api.member.musical.service;

import com.everyplaceinkorea.epik_boot3_api.entity.musical.Musical;
import com.everyplaceinkorea.epik_boot3_api.entity.musical.MusicalBookmark;
import com.everyplaceinkorea.epik_boot3_api.member.musical.dto.MusicalResponseDto;
import com.everyplaceinkorea.epik_boot3_api.repository.musical.MusicalBookmarkRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.musical.MusicalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMusicalService implements MusicalService {

    private final MusicalRepository musicalRepository;
    private final MusicalBookmarkRepository musicalBookmarkRepository;

    @Override
    public List<MusicalResponseDto> getBookmark(Long id) {
        List<MusicalBookmark> bookmarks = musicalBookmarkRepository.findMusicalBookmarksByMemberId(id);
        List<Musical> musicals = bookmarks.stream()
                .map(MusicalBookmark::getMusical)
                .collect(Collectors.toList());

        List<MusicalResponseDto> responseDtos = new ArrayList<>();

                musicals.forEach(Musical -> {
            Long musicalId = Musical.getId();
            Musical findMusical = musicalRepository.findById(musicalId).orElseThrow();
            MusicalResponseDto responseDto = MusicalResponseDto.builder()
                    .id(findMusical.getId())
                    .title(findMusical.getTitle())
                    .startDate(findMusical.getStartDate())
                    .endDate(findMusical.getEndDate())
                    .venue(findMusical.getVenue())
                    .saveImageName(findMusical.getFileSavedName())
                    .build();

            responseDtos.add(responseDto);

        });

        return responseDtos;
    }


}
