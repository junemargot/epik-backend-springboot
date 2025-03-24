package com.everyplaceinkorea.epik_boot3_api.entity.concert;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.config.Configuration;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ConcertImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_save_name")
    private String fileSaveName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Builder
    public ConcertImage(String fileSaveName, String filePath, Concert concert) {
        this.fileSaveName = fileSaveName;
        this.filePath = filePath;
        this.concert = concert;
    }
}
