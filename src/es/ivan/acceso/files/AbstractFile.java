package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.File;
import java.util.Arrays;

public abstract class AbstractFile {

    /**
     * Borra el archivo
     *
     * @param type El tipo de archivo al que estamos accediendo
     * @param fileName El archivo a ser borrado
     */
    public void removeFile(FileType type, String fileName) {
        final File file = this.getFile(type, fileName);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo borrado");
            } else {
                Log.error("No se ha podido borrar el archivo");
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName);
        }
    }

    /**
     * Muestra el contenido de la carpeta a la que accedemos dependiendo del tipo de búsqueda
     *
     * @param type El tipo de archivo al que estamos accediendo
     */
    public void showFileTree(FileType type) {
        final File parent = this.getFile(type, "").getParentFile();

        if (parent.listFiles().length == 0) {
            System.out.println("No hay archivos guardados");
            return;
        }
        System.out.println("Archivos:");
        Arrays.asList(parent.listFiles()).forEach(file -> System.out.println(file.getName()));
    }

    /**
     * Método interno para obtener el archivo en base a su nombre.
     *
     * @param type El tipo de archivo al que estamos accediendo
     * @param fileName El nombre del archivo
     * @return El archivo a buscar
     */
    public File getFile(FileType type, String fileName) {
        return new File("./files/" + type.getFolder() + "/" + fileName + type.getExt());
    }
}
