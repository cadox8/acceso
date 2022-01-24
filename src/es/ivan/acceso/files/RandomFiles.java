package es.ivan.acceso.files;

import es.ivan.acceso.api.Alumno;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;

public class  RandomFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public RandomFiles() {
        super(FileType.RANDOM);
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
                System.out.println("\n");
                final RandomAccessFile access = new RandomAccessFile(file, "r");
                access.seek(0);

                final byte[] bytes = new byte[(int)access.length()];

                access.readFully(bytes);

                Log.normal(((Alumno)new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject()).toString());

                access.close();
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".txt");
        }
    }

    /**
     * Escribe el contenido dentro del archivo
     *
     * @param fileName El archivo donde guardar el contenido
     * @param content El contenido a ser guardado
     */
    public void saveFile(String fileName, Alumno content) {
        final File file = this.getFile(fileName);

        if (!file.exists()) {
            try {
                final RandomAccessFile access = new RandomAccessFile(file, "rw");

                access.seek(access.length());

                final ByteArrayOutputStream writerArray = new ByteArrayOutputStream();
                final ObjectOutputStream writer = new ObjectOutputStream(writerArray);
                writer.writeObject(content);
                writer.flush();
                writer.close();

                access.write(writerArray.toByteArray());
                access.close();

                Log.success("Archivo guardado");
            } catch (IOException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".txt");
        }
    }
}
