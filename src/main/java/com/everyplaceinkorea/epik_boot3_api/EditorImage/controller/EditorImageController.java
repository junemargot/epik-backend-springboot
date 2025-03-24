package com.everyplaceinkorea.epik_boot3_api.EditorImage.controller;

import com.everyplaceinkorea.epik_boot3_api.EditorImage.UploadFolderType;
import com.everyplaceinkorea.epik_boot3_api.EditorImage.service.EditorImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// 에디터 이미지 반환
@RestController
@RequestMapping("editor-image")
@RequiredArgsConstructor
@Slf4j
public class EditorImageController {

    private final EditorImageService editorImageService;

    // 에디터 작성시 이미지파일 임시폴더에 업로드시키기
    @PostMapping("upload-temp")
    public String uploadImageToTmp(@RequestParam("domain") String domain,
                                    @RequestParam("fileName") MultipartFile imageName) {
        log.info("domain = {}", domain);
        log.info("imageName = {}", imageName);

        if (domain.equals("musical")) {
            return editorImageService.uploadImageToTmp(imageName, UploadFolderType.MUSICAL);
        } else if (domain.equals("concert")) {
            return editorImageService.uploadImageToTmp(imageName, UploadFolderType.CONCERT);
        } else if (domain.equals("popup")) {
            return editorImageService.uploadImageToTmp(imageName, UploadFolderType.POPUP);
        } else if (domain.equals("exhibition")) {
            return editorImageService.uploadImageToTmp(imageName, UploadFolderType.EXHIBITION);
        } else if (domain.equals("feed")) {
            return editorImageService.uploadImageToTmp(imageName, UploadFolderType.FEED);
        } else {
            throw new IllegalArgumentException("이미지 폴더타입이 잘못되었습니다.");
        }
    }

//    // 에디터 작성시 업로드한 이미지파일 다시 바이트로 반환해주기
//    @GetMapping(value = "return-byte/{domain}/{fileName}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    public byte[] printEditorImage(@PathVariable String domain,
//                                   @PathVariable(name = "fileName") String imageName) {
//
//        if (domain.equals("musical")) {
//            return editorImageService.getImageFromTmp(imageName, UploadFolderType.MUSICAL);
//        } else if (domain.equals("concert")) {
//            return editorImageService.getImageFromTmp(imageName, UploadFolderType.CONCERT);
//        } else if (domain.equals("popup")) {
//            return editorImageService.getImageFromTmp(imageName, UploadFolderType.POPUP);
//        } else if (domain.equals("exhibition")) {
//            return editorImageService.getImageFromTmp(imageName, UploadFolderType.EXHIBITION);
//        } else if (domain.equals("feed")) {
//            return editorImageService.getImageFromTmp(imageName, UploadFolderType.FEED);
//        } else {
//            throw new IllegalArgumentException("이미지 폴더타입이 잘못되었습니다.");
//        }
//
//    }
}
