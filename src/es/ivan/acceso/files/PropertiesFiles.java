package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public PropertiesFiles() {
        super(FileType.PROP);
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
                final FileReader reader = new FileReader(file);
                final Properties properties = new Properties();
                properties.load(reader);

                final Enumeration<Object> keys = properties.keys();
                while (keys.hasMoreElements()){
                    final Object key = keys.nextElement();
                    Log.normal(key + "=" + properties.get(key));
                }
                reader.close();
            } catch (IOException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".properties");
        }
    }

    /**
     * Edita una propiedad dentro del archivo
     *
     * @param fileName El archivo donde guardar el contenido
     * @param key La key a editar
     * @param value El valor editado
     */
    public void editFile(String fileName, String key, String value) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final FileReader reader = new FileReader(file);
                final Properties properties = new Properties();
                properties.load(reader);

                if (!properties.containsKey(key)) {
                    Log.error("No existe la key: " + key);
                    return;
                }

                properties.replace(key, value);

                final FileWriter writer = new FileWriter(file);
                properties.store(writer, "Archivo creado mediante app de Iván");

                //
                reader.close();
                writer.flush();
                writer.close();
                //

                Log.success("Archivo guardado");
            } catch (IOException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".properties");
        }
    }

    /**
     * Escribe el contenido dentro del archivo
     *
     * @param fileName El archivo donde guardar el contenido
     * @param proper Las propiedades que tiene el archivo a ser escrito
     */
    public void saveFile(String fileName, HashMap<String, String> proper) {
        final File file = this.getFile(fileName);

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

                Log.success("Archivo guardado");
            } catch (IOException e) {
                Log.error("Ha ocurrido un error inesperado");
               Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".properties");
        }
    }
}
