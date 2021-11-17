package es.ivan.acceso.files;

import es.ivan.acceso.api.Alumno;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;

public class BinFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public BinFiles() {
        super(FileType.BIN);
        final File parent = this.getFile("").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    /**
     * Muestra el contenido por consola del archivo buscado
     *
     * @param fileName El archivo a buscar
     */
    @Override
    public void showFileInfo(String fileName) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
                System.out.println(reader.readObject());
                final Alumno alumno = (Alumno) reader.readObject();
                System.out.println(alumno.toString());
                reader.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
    }
}
