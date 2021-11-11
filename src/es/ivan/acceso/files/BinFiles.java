package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public BinFiles() {
        final File parent = this.getFile(FileType.BIN, "").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    @Override
    public void showFileInfo(String fileName) {
        final File file = this.getFile(FileType.BIN, fileName);

        if (!file.exists()) {
            try {
                final ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
                Log.normal(reader.readObject().toString());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".properties");
        }
    }
}
