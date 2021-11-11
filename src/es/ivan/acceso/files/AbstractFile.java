package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.File;
import java.util.Arrays;

public abstract class AbstractFile {

    private final FileType type;

    public AbstractFile(FileType fileType) {
        this.type = fileType;
    }

    public abstract void showFileInfo(String fileName);

    /**
     * Borra el archivo
     *
     * @param fileName El archivo a ser borrado
     */
    public void removeFile(String fileName) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            if (file.delete()) {
                Log.success("Archivo borrado");
            } else {
                Log.error("No se ha podido borrar el archivo");
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName);
        }
    }

    /**
     * Muestra el contenido de la carpeta a la que accedemos dependiendo del tipo de búsqueda
     */
    public void showFileTree() {
        final File parent = this.getFile("").getParentFile();

        if (parent.listFiles().length == 0) {
            System.out.println("No hay archivos guardados");
            return;
        }
        Log.normal("Archivos:");
        Arrays.asList(parent.listFiles()).forEach(file -> Log.normal(file.getName()));
    }

    /**
     * Método interno para obtener el archivo en base a su nombre.
     *
     * @param fileName El nombre del archivo
     * @return El archivo a buscar
     */
    public File getFile(String fileName) {
        return new File("./files/" + this.type.getFolder() + "/" + fileName + this.type.getExt());
    }
}
