package com.everyplaceinkorea.epik_boot3_api.entity.exhibition;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExhibitionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_save_name")
    private String fileSaveName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public ExhibitionImage(String fileSaveName, String filePath, Exhibition exhibition) {
        this.fileSaveName = fileSaveName;
        this.filePath = filePath;
        this.exhibition = exhibition;
    }
}
