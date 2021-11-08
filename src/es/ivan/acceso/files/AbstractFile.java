package es.ivan.acceso.files;

import es.ivan.acceso.files.utils.FileType;
import es.ivan.acceso.files.utils.FileUtils;

public abstract class AbstractFile {

    protected final FileUtils fileUtils;

    public AbstractFile() {
        this.fileUtils = new FileUtils();
    }

    /**
     * Muestra el contenido de la carpeta a la que accedemos dependiendo del tipo de búsqueda
     */
    public void showFileTree() {
        this.fileUtils.showFileTree(FileType.PLAIN);
    }

    /**
     * Borra el archivo
     *
     * @param fileName El archivo a ser borrado
     */
    public void removeFile(String fileName) {
        this.fileUtils.removeFile(FileType.PLAIN, fileName);
    }
}
