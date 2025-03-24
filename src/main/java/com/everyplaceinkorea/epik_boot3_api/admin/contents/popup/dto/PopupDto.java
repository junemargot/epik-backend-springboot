package com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupTag;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupDto {
    // 필요한 데이터
    // 번호, 제목, 작성자, 등록일, 조회수, 총 게시물 수
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime writeDate;
    private Integer viewCount;
}
