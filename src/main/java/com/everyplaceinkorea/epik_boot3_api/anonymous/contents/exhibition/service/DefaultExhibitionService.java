package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.exhibition.dto.ExhibitionResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.exhibition.Exhibition;
import com.everyplaceinkorea.epik_boot3_api.repository.exhibition.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultExhibitionService implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExhibitionResponseDto> getExhibitionsByRegion(Long regionId, Integer page) {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        Page<Exhibition> musicals = exhibitionRepository.findExhibitionsByRegion(regionId, LocalDate.now(), pageable);
//        musicals.getContent().forEach(System.out::println);
        List<ExhibitionResponseDto> responseDtos = musicals
                .getContent()
                .stream()
                .map(Exhibition ->{
                    ExhibitionResponseDto responseDto = modelMapper.map(Exhibition, ExhibitionResponseDto.class);
                    return responseDto;
                })
                .toList();

        return responseDtos;
    }
    @Override
    public List<ExhibitionResponseDto> getExhibitionsByRandom() {
        List<Exhibition> exhibitions = exhibitionRepository.findExhibitionByRandom();
        exhibitions.forEach(Exhibition -> System.out.println(Exhibition.getTitle()));

        List<ExhibitionResponseDto> responseDtos = exhibitions
                .stream()
                .map(Exhibition ->{
                    ExhibitionResponseDto responseDto = modelMapper.map(Exhibition, ExhibitionResponseDto.class);
                    return responseDto;
                })
                .toList();

        return responseDtos;
    }
}

