package com.everyplaceinkorea.epik_boot3_api.admin.notice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/tui-editor")
public class NoticeFileApiController {

  // 파일을 업로드할 디렉토리 경로
  // /Users/jeongui/8th/upload
  private final String uploadDir = Paths.get("/Users/jeongui/8th/upload").toString();

  @PostMapping("/image-upload")
  public String uploadEditorImage(@RequestParam final MultipartFile image) {
    if (image.isEmpty()) {
      return "";
    }

    String originFileName = image.getOriginalFilename();
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    String extension = originFileName.substring(originFileName.lastIndexOf(".") + 1);
    String saveFileName = uuid + "." + extension;
    String fileFullPath = Paths.get(uploadDir, saveFileName).toString();

    // uploadDir에 해당되는 디렉토리가 없으면, uploadDir에 포함되는 전체 디렉토리 생성
    File dir = new File(uploadDir);
    if(dir.exists() == false) {
      dir.mkdirs();
    }

    try {
      // 파일 저장
      File uploadFile = new File(fileFullPath);
      image.transferTo(uploadFile);

      return saveFileName;
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
  public byte[] printEditorImage(@RequestParam final String fileName) {
    // 업로드된 파일의 전체 경로
    String fileFullPath = Paths.get(uploadDir, fileName).toString();

    // 파일이 없는 경우 예외 throw
    File uploadedFile = new File(fileFullPath);
    if(!uploadedFile.exists()) {
      throw new RuntimeException();
    }

    try {
      // 이미지 파일을 byte[]로 변환 후 반환
      byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
      return imageBytes;
    }catch(IOException e) {
      throw new RuntimeException(e);
    }


  }
}
