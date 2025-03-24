package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.enums.Status;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.entity.Region;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@Setter
public class Musical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String address;
    private String venue;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "file_saved_name")
    private String fileSavedName;

    @Column(name = "running_time")
    private String runningTime;

    @Column(name = "age_restriction")
    private String ageRestriction;

    @Column(name = "view_count")
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "write_date")
    @CreationTimestamp
    private LocalDateTime writeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 테이블 외래키(fk)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region; // 지역 테이블 외래키(fk)

    public void addMember(Member member) {
        this.member = member;
    }

    public void addRegion(Region region) {
        this.region = region;
    }

    public void addFileSavedName(String fileSavedName) {
        this.fileSavedName = fileSavedName;
    }

    // 상태 변경
    public void changeStatus() {
        this.status = (this.status == Status.ACTIVE) ? Status.HIDDEN : Status.ACTIVE;
    }

    // 삭제
    public void delete() {
        this.status = Status.DELETE;
    }


}

