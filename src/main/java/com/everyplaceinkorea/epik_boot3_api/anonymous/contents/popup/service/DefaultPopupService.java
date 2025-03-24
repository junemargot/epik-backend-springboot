package com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.contents.popup.dto.PopupResponseDto;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.Popup;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupImage;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupTag;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupImageRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPopupService implements PopupService {

    private final PopupRepository popupRepository;
    private final PopupTagRepository popupTagRepository;
    private final PopupImageRepository popupImageRepository;
    private final ModelMapper modelMapper;

    //카테고리별
    @Override
    public List<PopupResponseDto> getPopupsFindCategoryAndStartDate(Long categoryId, Integer page)  {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        LocalDate now = LocalDate.now();
        System.out.println("오늘날짜: " + now);
        Page<Popup> popups = popupRepository.findByCategoryAndStartDate(categoryId, LocalDate.now(), pageable);

        List<PopupResponseDto> responseDtos = popups
                .getContent()
                .stream()
                .map(Popup ->{
                    List<PopupImage> allByPopupId = popupImageRepository.findAllByPopupId(Popup.getId());
                    PopupResponseDto responseDto = modelMapper.map(Popup, PopupResponseDto.class);
                    responseDto.setImgSavedName(allByPopupId.get(0).getImgSavedName());
                    System.out.println("Mapped DTO: " + responseDto); // 변환된 DTO 출력
                    return responseDto;
                })
                .toList();

        return responseDtos;

    }

    //지역별
    @Override
    public List<PopupResponseDto> getPopupsFindByRegionAndStartDate(Long regionId, Integer page)  {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        LocalDate now = LocalDate.now();
        System.out.println("오늘날짜: " + now);
        Page<Popup> popups = popupRepository.findByRegionAndStartDate(regionId, LocalDate.now(), pageable);

        List<PopupResponseDto> responseDtos = popups
                .getContent()
                .stream()
                .map(Popup ->{
                    PopupResponseDto responseDto = modelMapper.map(Popup, PopupResponseDto.class);
                    System.out.println("Mapped DTO: " + responseDto); // 변환된 DTO 출력
                    return responseDto;
                })
                .toList();

        return responseDtos;

    }

    //카테고리 선택 후 지역 선택
    @Override
    public List<PopupResponseDto> getPopupsFindByCategoryAndRegionAndStartDate(Long categoryId, Long regionId, Integer page) {

        // 정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        // 페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);

        LocalDate now = LocalDate.now();
        System.out.println("오늘날짜: " + now);
        Page<Popup> popups = popupRepository.findByCategoryAndRegionAndStartDate(categoryId, regionId, LocalDate.now(), pageable);

        log.info("popups ={}", popups.toString());

        List<PopupResponseDto> responseDtos = popups
                .getContent()
                .stream()
                .map(Popup -> {
                    List<PopupImage> allByPopupId = popupImageRepository.findAllByPopupId(Popup.getId());
                    PopupResponseDto responseDto = modelMapper.map(Popup, PopupResponseDto.class);
                    responseDto.setImgSavedName(allByPopupId.get(0).getImgSavedName());
                    System.out.println("Mapped DTO: " + responseDto); // 변환된 DTO 출력
                    return responseDto;
                })
                .toList();

        log.info("responseDto = {}", responseDtos.toString());
        return responseDtos;
    }

    //epik pick 랜덤이미지조회
    @Override
    public List<PopupResponseDto> getPopupsByRandom () {
        List<Popup> popups = popupRepository.getPopupsByRandom();
        popups.forEach(popup -> System.out.println(popup.getTitle()));

        List<PopupResponseDto> responseDtos = popups
                .stream()
                .map(Popup -> {
                    List<PopupImage> allByPopupId = popupImageRepository.findAllByPopupId(Popup.getId());
                    PopupResponseDto responseDto = modelMapper.map(Popup, PopupResponseDto.class);
                    responseDto.setImgSavedName(allByPopupId.get(0).getImgSavedName());
                    return responseDto;
                })
                .toList();



        return responseDtos;
    }

    //팝업 상세조회
    @Override
    public PopupResponseDto getPopup(Long id) {
        Popup popup = popupRepository.findById(id).orElseThrow();
        log.info("popup = {}", popup);
        //태그
        List<PopupTag> allByPopupTag = popupTagRepository.findAllByPopupId(popup.getId());
        String[] tags = new String[allByPopupTag.size()];
        for (int i = 0; i < allByPopupTag.size(); i++) {
            tags[i] = allByPopupTag.get(i).getTag();
        }

        List<PopupImage> allByPopupImage = popupImageRepository.findAllByPopupId(popup.getId());
        String[] saveImageNames = new String[allByPopupImage.size()];
        for (int i = 0; i < allByPopupImage.size(); i++) {
            saveImageNames[i] = allByPopupImage.get(i).getImgSavedName();
        }


        PopupResponseDto responseDto = modelMapper.map(popup, PopupResponseDto.class);
        responseDto.setTags(tags);
        responseDto.setSaveImageNames(saveImageNames);

        return responseDto;
    }
}
