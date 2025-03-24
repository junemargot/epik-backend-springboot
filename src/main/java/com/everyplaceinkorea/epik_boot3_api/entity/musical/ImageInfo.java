package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInfo {
    @Column(name = "file_saved_name")
    private String fileSavedName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_origin_name")
    private String fileOriginName;

    public ImageInfo(String fileOriginName, String fileSavedName, String filePath) {
        this.fileOriginName = fileOriginName;
        this.fileSavedName = fileSavedName;
        this.filePath = filePath;
    }

}
