package com.everyplaceinkorea.epik_boot3_api.repository.popup;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupTagRepository extends JpaRepository<PopupTag, Long> {
    List<PopupTag> findAllByPopupId(Long id);
}
