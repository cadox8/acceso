package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;

public class RandomFiles extends AbstractFile {

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
                final BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while((line = br.readLine()) != null) Log.normal(line);
                br.close();
            } catch (IOException e) {
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
    public void saveFile(String fileName, String content) {
        final File file = this.getFile(fileName);

        if (!file.exists()) {
            try {
                final BufferedWriter br = new BufferedWriter(new FileWriter(file));

                br.write(content);
                br.flush();
                br.close();
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
