package es.ivan.acceso.old.files.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileType {

    PLAIN("plain", ".txt"),
    PROP("properties", ".properties"),
    BIN("bin", ".bin"),
    XML("xml", ".xml"),
    RANDOM("random", PLAIN.ext);

    @Getter
    private final String folder;
    @Getter
    private final String ext;
}
