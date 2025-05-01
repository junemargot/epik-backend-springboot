package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.musical.dto.MusicalResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.musical.Musical;
import com.everyplaceinkorea.epik_boot3_api.repository.musical.MusicalRepository;
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
public class DefaultMusicalService implements MusicalService{

    private final MusicalRepository musicalRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MusicalResponseDto> getMusicalsByRegion(Long regionId, Integer page) {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        Page<Musical> musicals = musicalRepository.findMusicalsByRegion(regionId, LocalDate.now(), pageable);
        List<MusicalResponseDto> responseDtos = musicals
                .getContent()
                .stream()
                .map(Musical ->{
                    MusicalResponseDto responseDto = modelMapper.map(Musical, MusicalResponseDto.class);
                    return responseDto;
                })
                .toList();

        return responseDtos;
    }

    // 뮤지컬 랜덤 조회
    @Override
    public List<MusicalResponseDto> getMusicalsByRandom() {
      LocalDate today = LocalDate.now();
      List<Musical> musicals = musicalRepository.findActiveMusicalByRandom(today);

      return musicals.stream()
              .map(musical -> modelMapper.map(musical, MusicalResponseDto.class))
              .toList();
    }
}
