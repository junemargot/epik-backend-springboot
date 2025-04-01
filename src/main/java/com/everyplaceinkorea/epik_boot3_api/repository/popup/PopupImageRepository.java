package com.everyplaceinkorea.epik_boot3_api.repository.popup;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopupImageRepository extends JpaRepository<PopupImage, Long> {

    // 특정 팝업 ID에 연결된 모든 이미지를 조회하는 메서드
    List<PopupImage> findAllByPopupId(Long id);

    // 특정 팝업 ID에 연결된 모든 이미지를 삭제하는 커스텀 쿼리 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM PopupImage pi WHERE pi.popup.id = :popupId")
    void deleteAllByPopupId(@Param("popupId") Long id);
}
