package com.everyplaceinkorea.epik_boot3_api.member.exhibition.service;

import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.Exhibition;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.ExhibitionBookmark;
import com.everyplaceinkorea.epik_boot3_api.member.exhibition.dto.ExhibitionResponseDto;
import com.everyplaceinkorea.epik_boot3_api.repository.exhibition.ExhibitionBookmarkRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.exhibition.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultExhibitionService implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionBookmarkRepository ExhibitionBookmarkRepository;

    @Override
    public List<ExhibitionResponseDto> getBookmark(Long id) {
        List<ExhibitionBookmark> bookmarks = ExhibitionBookmarkRepository.findExhibitionBookmarksByMemberId(id);
        List<Exhibition> exhibitions = bookmarks.stream()
                .map(ExhibitionBookmark::getExhibition)
                .collect(Collectors.toList());

        List<ExhibitionResponseDto> responseDtos = new ArrayList<>();

        exhibitions.forEach(exhibition -> {
            Long exhibitionId = exhibition.getId();
                    Exhibition findExhibition = exhibitionRepository.findById(exhibitionId).orElseThrow();
                    ExhibitionResponseDto responseDto = ExhibitionResponseDto.builder()
                    .id(findExhibition.getId())
                    .title(findExhibition.getTitle())
                    .startDate(findExhibition.getStartDate())
                    .endDate(findExhibition.getEndDate())
                    .venue(findExhibition.getVenue())
                    .saveImageName(findExhibition.getFileSavedName())
                    .build();

            responseDtos.add(responseDto);

        });

        return responseDtos;
    }


}
