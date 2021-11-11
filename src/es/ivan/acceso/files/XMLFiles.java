package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;

import java.io.File;

public class XMLFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public XMLFiles() {
        final File parent = this.getFile(FileType.XML, "").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    @Override
    public void showFileInfo(String fileName) {
        final File file = this.getFile(FileType.XML, fileName);
    }
}
