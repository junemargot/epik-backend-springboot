package com.everyplaceinkorea.epik_boot3_api.repository.popup;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupImageRepository extends JpaRepository<PopupImage, Long> {
    List<PopupImage> findAllByPopupId(Long id);
}
