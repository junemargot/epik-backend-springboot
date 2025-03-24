package com.everyplaceinkorea.epik_boot3_api.EditorImage.service;

import com.everyplaceinkorea.epik_boot3_api.EditorImage.UploadFolderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class EditorImageService {

    public String uploadImageToTmp(MultipartFile imgFile, UploadFolderType uploadFolderType) {
        if (imgFile != null && !imgFile.isEmpty()) {
            String originImageName = imgFile.getOriginalFilename(); // 실제 파일명
            String extension = originImageName.substring(originImageName.lastIndexOf(".")); // 확장자
            String saveimgFileName = UUID.randomUUID().toString().replace("-", "") + extension; // 실제로 저장할 파일명

            File folder = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "temp" + File.separator + uploadFolderType.getFolderName()); // 최종 저장 경로 폴더

            // 최종 저장 경로 폴더가 존재하는지 확인 및 존재하지 않으면 생성해주기
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    throw new IllegalStateException("에디터의 작성시 이미지를 임시폴더에 저장하려는데 폴더를 만들수가 없음.");
                }
            }

            String fullPath = folder.getAbsolutePath() + File.separator + saveimgFileName; // 이미지 파일의 최종 경로

            // unix 계열일 경우 구분자 변경
            if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                fullPath = fullPath.replace("\\", "/");
            }

            log.info("반환값 = {}" , saveimgFileName);

            try {
                imgFile.transferTo(new File(fullPath)); // 파일 저장하기
                return saveimgFileName; // 파일명 반환
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return null;

    }
}
