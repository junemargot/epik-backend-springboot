package com.everyplaceinkorea.epik_boot3_api.repository.popup;

import com.everyplaceinkorea.epik_boot3_api.entity.popup.PopupTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopupTagRepository extends JpaRepository<PopupTag, Long> {

    // 특정 팝업 ID에 연결된 모든 태그를 조회
    List<PopupTag> findAllByPopupId(Long id);

    // 특정 팝업 ID에 연결된 모든 태그를 삭제하는 커스텀 쿼리 메서드
    // @Modifying: 데이터 변경 작업 수행
    // @Transactional: 트랜잭션 내에 실행
    // @Query: 실행할 JPQL 쿼리 정의
    // @Param: 메서드 파라미터와 쿼리의 명명된 파라미터(:popupId)를 매핑
    @Modifying
    @Transactional
    @Query("DELETE FROM PopupTag pt WHERE pt.popup.id = :popupId")
    void deleteAllByPopupId(@Param("popupId") Long id);
}
