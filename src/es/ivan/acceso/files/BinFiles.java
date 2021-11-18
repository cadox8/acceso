package es.ivan.acceso.files;

import es.ivan.acceso.api.BinParser;
import es.ivan.acceso.api.Alumno;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;
import java.util.HashMap;

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
                final ObjectInputStream reader = new BinParser(new FileInputStream(file), "dam.AD", "es.ivan.acceso.api");
                final HashMap<String, Alumno> alumnos = (HashMap<String, Alumno>) reader.readObject();

                alumnos.forEach((name, alum) -> {
                    Log.normal(name + ":");
                    final StringBuilder sb = new StringBuilder();
                    sb.append("  Nombre: " + alum.getNombre());
                    sb.append("\n  Asignatura: " + alum.getAsignatura());
                    sb.append("\n  Curso: " + alum.getCurso());
                    sb.append("\n  Aprobado: " + (alum.getAprobado() ? "Sí" : "No"));
                    sb.append("\n  Nota: " + alum.getNota());
                    sb.append('\n');
                    Log.normal(sb.toString());
                });

                reader.close();
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                e.printStackTrace();
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
    }
}
