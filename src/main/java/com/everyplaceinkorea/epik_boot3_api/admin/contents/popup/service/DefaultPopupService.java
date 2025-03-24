package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.service;

import com.everyplaceinkorea.epik_boot3_api.EditorImage.UploadFolderType;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.*;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.entity.popup.*;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import com.everyplaceinkorea.epik_boot3_api.repository.popup.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.PagesPerMinute;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPopupService implements PopupService {

    private final PopupRepository popupRepository;
    private final PopupCategoryRepository popupCategoryRepository;
    private final PopupRegionRepository popupRegionRepository;
    private final PopupTagRepository popupTagRepository;
    private final MemberRepository memberRepository;
    private final PopupImageRepository popupImageRepository;
    private final ModelMapper modelMapper;

    @Value("${file.tmp-dir}")
    private String tmpPath;

    @Value("${file.upload-dir}")
    private String uploadPath;

    // 팝업 등록
    @Transactional
    @Override
    public Long create(PopupRequestDto popupRequestDto, MultipartFile[] files) throws IOException {
        // 작성자 임시로 값 담아주기
        popupRequestDto.setWriter(1L);
        popupRequestDto.setType("팝업");

        // 멤버 외래키
        Member member = memberRepository.findById(popupRequestDto.getWriter()).orElseThrow();
        // 카테고리 외래키
        PopupCategory popupCategory = popupCategoryRepository.findById(popupRequestDto.getWriter()).orElseThrow();
        // 지역 외래키
        PopupRegion popupRegion = popupRegionRepository.findById(popupRequestDto.getPopupRegion()).orElseThrow();

        // dto -> entity
        Popup popup = modelMapper.map(popupRequestDto, Popup.class);
        popup.addMember(member); // entity 담아주기
        popup.addPopupCategory(popupCategory);
        popup.addPopupRegion(popupRegion);

        // 팝업 테이블 데이터 저장하기
        Popup savedPopup = popupRepository.save(popup);

        /////////////////////////////////////////////////////////////////////////////////

        // tags들을 뽑아서 tag테이블에 save
        String[] tags = popupRequestDto.getTags();
        for (String tag: tags) {
            PopupTag popupTag = PopupTag.builder()
                    .tag(tag)
                    .popup(savedPopup)
                    .build();
            popupTagRepository.save(popupTag);
        }

        //////////////////////////////////////////////////////////////////////////////////

        // files에 담긴 파일들 뽑아서 popup_image테이블에 save
        int orderIndex = 0;
        if (files.length > 0) {
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "") + extension;

                File folder = new File(System.getProperty("user.dir") + "/" + uploadPath + "/" + UploadFolderType.POPUP.getFolderName());

                if (!folder.exists()) {
                    if(!folder.mkdirs()) {
                        throw new IllegalArgumentException("이미지 저장 폴더 생성에 실패 하였습니다.");
                    }
                }

                String fullPath = folder.getAbsolutePath() + File.separator + savedFileName;
                try {
                    file.transferTo(new File(fullPath));
                } catch (IOException e) {
                    log.info(e.getMessage());
                }

                PopupImageDto imageDto = PopupImageDto.builder()
                        .imgOrder(++orderIndex)
                        .imgSavedName(savedFileName)
                        .build();


                PopupImage popupImage = modelMapper.map(imageDto, PopupImage.class);
                popupImage.setPopup(savedPopup);
                popupImageRepository.save(popupImage);

                log.info("첨부파일 데이터베이스에 저장할 이름 savedFieName: {}", savedFileName);
                log.info("첨부파일 folder: {}", folder.getAbsolutePath());
                log.info("첨부파일 fullPath: {}", fullPath);
                log.info("첨부파일 imageDto: {}", imageDto);
                log.info("첨부파일 popupImage: {}", popupImage);
            }
        }

        // fileNames에서 뽑아서 업로드 폴더로 옮겨주기
//        String[] fileNames = popupRequestDto.getFileNames();
//        for (String fileName : fileNames) {
//            log.info("#fileName: {}", fileName);
//            // ### 현재 임시폴더에 있는 이미지 의 경로생성하기 ###
//            // C:\Users\yunkk\Desktop\epik-full\epik\backend\src\main\webapp\image\tmp\musical
//            Path folderPath = Paths.get(System.getProperty("user.dir") + File.separator + tmpPath + File.separator + UploadFolderType.POPUP.getFolderName());
//            log.info("folderPath ={} ",folderPath);
//            // C:\Users\yunkk\Desktop\epik-full\epik\backend\src\main\webapp\image\tmp\musical\fe0e5a9fc11a45f4aa3b73ae2a9f0816.jpg
//            String fullPath = System.getProperty("user.dir") + File.separator + tmpPath + File.separator + UploadFolderType.POPUP.getFolderName() + File.separator + fileName;
//            log.info("fullPath ={} ",fullPath);
//            Path sourcePath = Paths.get(fullPath);
//            log.info("sourcePath ={} ",sourcePath);
//
//            Path targetpath = Paths.get(System.getProperty("user.dir") + File.separator + uploadPath + File.separator + UploadFolderType.POPUP.getFolderName() + File.separator + fileName);
//            log.info("targetpath ={} ",targetpath);
//
//            if (Files.exists(sourcePath)) {
//                log.info("파일명 : {}이 임시폴더에 존재합니다.", fileName);
//                // 존재한다면? webapp/image/uplods/musical 로 옮기기
//                // 일단 폴더가 존재하는지 확인하고
//                if (!Files.exists(folderPath)) {
//                    Files.createDirectories(folderPath); // 상위 폴더까지 모두 생성
//                }
//                // 이동할 파일의 현재 경로(sourceDir), 이동 후의 파일 경로 설정(targetDir)
////                Files.move(sourcePath, targetpath);
//            }
//
//        }
//
//        log.info("저장된 팝업 식별 번호 ={}", savedPopup.getId());

        return savedPopup.getId();
    }


    // 팝업 삭제
    @Override
    public void delete(Long id) {
        Popup popup = popupRepository.findById(id).orElseThrow();
        popup.delete();

        popupRepository.save(popup);
    }

    @Override
    public void updatePopupStatus(Long id) {
        Popup popup = popupRepository.findById(id).orElseThrow();
//        popup.setStatus(popup.getStatus() == Status.HIDDEN ? Status.ACTIVE : Status.HIDDEN);
        popup.changeStatus();
        popupRepository.save(popup);
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
        responseDto.setWriter(popup.getMember().getNickname());
        responseDto.setTags(tags);
        responseDto.setSaveImageNames(saveImageNames);

        return responseDto;
    }

    // 팝업 전체 조회 및 검색 기능
    @Override
    public PopupListDto getList(int page, String keyword, String searchType) {
        //정렬 기준 만들기
        Sort sort = Sort.by("id").descending();
        //페이징조건 만들기
        Pageable pageable = PageRequest.of(page - 1, 15, sort);
        // repository 전체조회 메서드 호출하기
        Page<Popup> popupPage = popupRepository.searchPopup(keyword, searchType, pageable);
        //반환받은 entity -> dto 로 변환해 controller로 반환하기
        List<PopupDto> popupDtos = popupPage
                .getContent()
                .stream().map(popup -> {
                    PopupDto popupDto = modelMapper.map(popup, PopupDto.class);
                    popupDto.setWriter(popup.getMember().getNickname());
                    return popupDto;
                }).toList();

        PopupListDto popupListDto = new PopupListDto();
        popupListDto.setPopupList(popupDtos);
        popupListDto.setTotalCount(popupPage.getTotalElements());

        return popupListDto;
    }


    @Override
    public void update(Long id, PopupRequestDto popupRequestDto) {
//        Popup popup = popupRepository.findById(id).orElseThrow();
//        Member writer = memberRepository.findById(popupRequestDto.getWriter()).orElseThrow();
//        PopupCategory popupCategory = popupCategoryRepository.findById(popupRequestDto.getWriter()).orElseThrow();
//        PopupRegion popupRegion = popupRegionRepository.findById(popupRequestDto.getPopupRegion()).orElseThrow();
//
//        popup.updatePopup(popupRequestDto, writer, popupCategory, popupRegion);
//
//        List<PopupTag> popupTags = updatePopupTags(popupRequestDto.getTags());
//        popupTagRepository.saveAll(popupTags);

    }

    private List<PopupTag> updatePopupTags(List<PopupTagDto> tagDtos) {
//        List<PopupTag> updatedTags = new ArrayList<>();
//        if (tagDtos != null) {
//            for (PopupTagDto dto : tagDtos) {
//                PopupTag tag = popupTagRepository.findById(dto.getId())
//                        .orElseThrow();
//                tag.updatePopupTag(dto);
//                updatedTags.add(tag);
//            }
//        }
//        return updatedTags;
        return null;
    }
}

