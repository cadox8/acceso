package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;
import java.text.FieldPosition;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public PropertiesFiles() {
        final File parent = this.getFile(FileType.PROP, "").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    /**
     * Muestra el contenido por consola del archivo buscado
     *
     * @param fileName El archivo a buscar
     */
    public void showFileInfo(String fileName) {
        final File file = this.getFile(FileType.PROP, fileName);

        if (file.exists()) {
            try {
                System.out.println("\n");
                final FileReader reader = new FileReader(file);
                final Properties properties = new Properties();
                properties.load(reader);

                final Enumeration<Object> keys = properties.keys();
                while (keys.hasMoreElements()){
                    final Object key = keys.nextElement();
                    System.out.println(key + "=" + properties.get(key));
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".properties");
        }
    }

    /**
     * Escribe el contenido dentro del archivo
     *
     * @param fileName El archivo donde guardar el contenido
     * @param proper
     */
    public void saveFile(String fileName, HashMap<String, String> proper) {
        final File file = this.getFile(FileType.PROP, fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();

                final FileReader reader = new FileReader(file);
                final FileWriter writer = new FileWriter(file);

                final Properties properties = new Properties();
                properties.load(reader);
                proper.keySet().forEach(k -> properties.setProperty(k, proper.get(k)));
                properties.store(writer, "Archivo creado mediante app de Iván");

                //
                reader.close();
                writer.flush();
                writer.close();
                //

                Log.normal("Archivo guardado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".properties");
        }
    }
}
