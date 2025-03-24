package com.everyplaceinkorea.epik_boot3_api.EditorImage;

public enum UploadFolderType {
    MUSICAL("musical"),
    CONCERT("concert"),
    POPUP("popup"),
    EXHIBITION("exhibition"),
    FEED("feed");

    private final String value;

    UploadFolderType(final String value) {
        this.value = value;
    }

    public String getFolderName() {
        return value;
    }
}
