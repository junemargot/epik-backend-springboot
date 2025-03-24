package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.concert.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.repository.concert.ConcertRepository;
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
public class DefaultConcertService implements ConcertService {

    private final ConcertRepository concertRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ConcertResponseDto> getConcertsByRegion(Long regionId, Integer page) {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        Page<Concert> concerts = concertRepository.findConcertsByRegion(regionId, LocalDate.now(), pageable);
//        concerts.getContent().forEach(System.out::println);
        List<ConcertResponseDto> responseDtos = concerts
                .getContent()
                .stream()
                .map(Concert ->{
                    ConcertResponseDto responseDto = modelMapper.map(Concert, ConcertResponseDto.class);
                    return responseDto;
                })
                .toList();

        return responseDtos;
    }

    @Override
    public List<ConcertResponseDto> getConcertsByRandom() {
        List<Concert> concerts = concertRepository.findConcertByRandom();
        concerts.forEach(concert -> System.out.println(concert.getTitle()));

        List<ConcertResponseDto> responseDtos = concerts
                .stream()
                .map(Concert ->{
                    ConcertResponseDto responseDto = modelMapper.map(Concert, ConcertResponseDto.class);
                    return responseDto;
                })
                .toList();

        return responseDtos;
    }
}
