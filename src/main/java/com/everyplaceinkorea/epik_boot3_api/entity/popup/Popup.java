package com.everyplaceinkorea.epik_boot3_api.entity.popup;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.dto.PopupRequestDto;
import com.everyplaceinkorea.epik_boot3_api.admin.contents.popup.enums.Status;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Popup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;
    private String address;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "sns_link")
    private String snsLink;

    @Column(name = "web_link")
    private String webLink;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
//    @JsonIgnore // 이 필드를 json 직렬화에서 제외
    private PopupCategory popupCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
//    @JsonIgnore // 이 필드를 json 직렬화에서 제외
    private PopupRegion popupRegion;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tag_id", nullable = false)
//    private PopupTag popupTag;  // Tag 클래스가 올바르게 import되어야 함.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "write_date")
    @CreationTimestamp
    private LocalDateTime writeDate;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "operation_time")
    private String operationTime;

    @Column(name = "type")
    private String type;

    public void addMember(Member member) {
        this.member = member;
    }

    public void addPopupCategory(PopupCategory popupCategory) {
        this.popupCategory = popupCategory;
    }

    public void addPopupRegion(PopupRegion popupRegion) {
        this.popupRegion = popupRegion;
    }

    // 삭제
    public void delete() {
        this.status = Status.DELETE;
    }

    // 상태 변경
    public void changeStatus() {
        this.status = (this.status == Status.ACTIVE) ? Status.HIDDEN : Status.ACTIVE;
    }

    //수정 메서드
    public void updatePopup(PopupRequestDto popupRequestDto, Member member,
                            PopupCategory popupCategory, PopupRegion popupRegion) {
        this.title = popupRequestDto.getTitle();
        this.content = popupRequestDto.getContent();
        this.address = popupRequestDto.getAddress();
        this.startDate = popupRequestDto.getStartDate();
        this.endDate = popupRequestDto.getEndDate();
        this.snsLink = popupRequestDto.getSnsLink();
        this.webLink = popupRequestDto.getWebLink();
        this.member = member;
        this.popupCategory = popupCategory;
        this.popupRegion = popupRegion;
        this.addressDetail = popupRequestDto.getAddressDetail();
    }
}