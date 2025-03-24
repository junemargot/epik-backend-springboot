package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MusicalImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_save_name")
    private String fileSaveName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    @Builder
    public MusicalImage(String fileSaveName, Musical musical) {
        this.fileSaveName = fileSaveName;
        this.musical = musical;
    }
}
