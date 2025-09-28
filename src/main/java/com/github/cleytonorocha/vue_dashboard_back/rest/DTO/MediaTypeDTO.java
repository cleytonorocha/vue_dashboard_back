package com.github.cleytonorocha.vue_dashboard_back.rest.DTO;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum for report media types with code
 */
public enum MediaTypeDTO {

    PDF(1, "pdf", "application/pdf"),
    XLSX(2, "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV(3, "csv", "text/csv");

    private final Integer code;
    private final String extension;
    private final String mediaType;

    MediaTypeDTO(Integer code, String extension, String mediaType) {
        this.code = code;
        this.extension = extension;
        this.mediaType = mediaType;
    }

    public Integer getCode() {
        return code;
    }

    public String getExtension() {
        return extension;
    }

    public String getMediaType() {
        return mediaType;
    }

    /**
     * Converts a string to enum (case-insensitive) using Stream
     */
    public static MediaTypeDTO fromString(String type) {
        return Arrays.stream(MediaTypeDTO.values())
                .filter(m -> m.extension.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid media type: " + type));
    }

    /**
     * Converts a code to enum
     */
    @JsonCreator
    public static MediaTypeDTO fromCode(Integer code) {
        return Arrays.stream(MediaTypeDTO.values())
                .filter(m -> m.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid media type code: " + code));
    }

}
