package es.ivan.acceso.files.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileType {
    PLAIN("plain", ".txt"),
    PROP("properties", ".properties");

    @Getter private final String folder;
    @Getter private final String ext;
}
