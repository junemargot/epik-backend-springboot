package com.everyplaceinkorea.epik_boot3_api.member.popup.service;

import com.everyplaceinkorea.epik_boot3_api.entity.concert.Concert;
import com.everyplaceinkorea.epik_boot3_api.entity.concert.ConcertBookmark;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.Popup;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupBookmark;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupImage;
import com.everyplaceinkorea.epik_boot3_api.member.concert.dto.ConcertResponseDto;
import com.everyplaceinkorea.epik_boot3_api.member.popup.dto.PopupResponseDto;
import com.everyplaceinkorea.epik_boot3_api.repository.concert.ConcertBookmarkRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.concert.ConcertRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupBookmarkRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupImageRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.PopupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPopupService implements PopupService {

    private final PopupRepository popupRepository;
    private final PopupImageRepository popupImageRepository;
    private final PopupBookmarkRepository popupBookmarkRepository;

    @Override
    public List<PopupResponseDto> getBookmark(Long id) {
        List<PopupBookmark> bookmarks = popupBookmarkRepository.findPopupBookmarksByMemberId(id);
        List<Popup> popups = bookmarks.stream()
                .map(PopupBookmark::getPopup)
                .collect(Collectors.toList());

        List<PopupResponseDto> responseDtos = new ArrayList<>();

        popups.forEach(popup -> {
        Long popupId = popup.getId();
        Popup findPopup = popupRepository.findById(popupId).orElseThrow();
            List<PopupImage> popupImages = popupImageRepository.findAllByPopupId(findPopup.getId());
            String ImageSaveName = popupImages.get(0).getImgSavedName();
            PopupResponseDto responseDto = PopupResponseDto.builder()
                    .id(findPopup.getId())
                    .title(findPopup.getTitle())
                    .startDate(findPopup.getStartDate())
                    .endDate(findPopup.getEndDate())
                    .venue(findPopup.getAddress())
                    .saveImageName(ImageSaveName)
                    .build();

            responseDtos.add(responseDto);
        });

        return responseDtos;
    }


}
