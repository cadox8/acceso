package es.ivan.acceso.files;

import es.ivan.acceso.files.utils.FileType;
import es.ivan.acceso.files.utils.FileUtils;
import es.ivan.acceso.utils.Log;

import java.io.*;

public class PlainFiles extends AbstractFile {
    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public PlainFiles() {
        final File parent = this.fileUtils.getFile(FileType.PLAIN, "").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    /**
     * Muestra el contenido por consola del archivo buscado
     *
     * @param fileName El archivo a buscar
     */
    public void showFileInfo(String fileName) {
        final File file = this.fileUtils.getFile(FileType.PLAIN, fileName);

        if (file.exists()) {
            try {
                System.out.println("\n");
                final BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while((line = br.readLine()) != null) System.out.println(line);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
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
        final File file = this.fileUtils.getFile(FileType.PLAIN, fileName);

        if (!file.exists()) {
            try {
                final BufferedWriter br = new BufferedWriter(new FileWriter(file));

                br.write(content);
                br.flush();
                br.close();
                Log.normal("Archivo guardado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".txt");
        }
    }
}
