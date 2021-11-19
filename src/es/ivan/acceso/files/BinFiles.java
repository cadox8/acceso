package es.ivan.acceso.files;

import es.ivan.acceso.api.BinParser;
import es.ivan.acceso.api.Alumno;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

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
                    sb.append("  1. Nombre: " + alum.getNombre());
                    sb.append("\n  2. Asignatura: " + alum.getAsignatura());
                    sb.append("\n  3. Curso: " + alum.getCurso());
                    sb.append("\n  4. Aprobado: " + (alum.getAprobado() ? "Sí" : "No"));
                    sb.append("\n  5. Nota: " + alum.getNota());
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

    /**
     * Muestra los nombres de los alumnos del fichero
     *
     * @param fileName El fichero
     */
    public void showAlumnos(String fileName) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final ObjectInputStream reader = new BinParser(new FileInputStream(file), "dam.AD", "es.ivan.acceso.api");
                final HashMap<String, Alumno> alumnos = (HashMap<String, Alumno>) reader.readObject();

                alumnos.keySet().forEach(Log::normal);

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

    /**
     * Muestra el contenido por consola del alumno en el archivo buscado
     *
     * @param fileName El archivo a buscar
     * @param alumno El alumno a ser buscado
     * @return True if the alumno exists
     */
    public boolean showAlumno(String fileName, String alumno) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final ObjectInputStream reader = new BinParser(new FileInputStream(file), "dam.AD", "es.ivan.acceso.api");
                final HashMap<String, Alumno> alumnos = (HashMap<String, Alumno>) reader.readObject();

                if (!alumnos.containsKey(alumno)) {
                    Log.error("No existe el alumno " + alumno);
                    return false;
                }

                final Alumno alum = alumnos.get(alumno);

                Log.normal(alumno + ":");
                final StringBuilder sb = new StringBuilder();
                sb.append("  1. Nombre: " + alum.getNombre());
                sb.append("\n  2. Asignatura: " + alum.getAsignatura());
                sb.append("\n  3. Curso: " + alum.getCurso());
                sb.append("\n  4. Aprobado: " + (alum.getAprobado() ? "Sí" : "No"));
                sb.append("\n  5. Nota: " + alum.getNota());
                sb.append("\n  6. Borrar");
                sb.append('\n');
                Log.normal(sb.toString());

                reader.close();
                return true;
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                e.printStackTrace();
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
        return false;
    }


    /**
     * Edita una propiedad dentro del archivo
     *
     * @param fileName El archivo donde guardar el contenido
     * @param alumno La alumno a editar
     * @param selection La selección de que editar
     * @param value El valor editado
     */
    public void editFile(String fileName, String alumno, int selection, String value) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final ObjectInputStream reader = new BinParser(new FileInputStream(file), "dam.AD", "es.ivan.acceso.api");
                final HashMap<String, Alumno> alumnos = (HashMap<String, Alumno>) reader.readObject();

                if (!alumnos.containsKey(alumno)) {
                    Log.error("No existe el alumno " + alumno);
                    return;
                }

                final Alumno alum = alumnos.get(alumno);

                switch (selection) {
                    case 1:
                        alum.setNombre(value);
                        break;
                    case 2:
                        alum.setAsignatura(value);
                        break;
                    case 3:
                        alum.setCurso(value);
                        break;
                    case 4:
                        alum.setAprobado(value.equalsIgnoreCase("s"));
                        break;
                    case 5:
                        float nota = 0;
                        try {
                            nota = Float.parseFloat(value);
                        } catch (NumberFormatException e) {
                            nota = 0;
                            Log.error("Nota inválida, 0 por defecto");
                            Log.stack(e.getStackTrace());
                        }

                        if (nota < 0 || nota > 10) {
                            nota = 0;
                            Log.error(nota + " > 10 ó < 0. Poniendo 0 por defecto");
                        }

                        alum.setNota(nota);
                        alum.setAprobado(nota >= 5);
                        break;
                    case 6:
                        break;

                    default:
                        Log.error("No has pasado un valor válido");
                        break;
                }

                if (selection == 6) {
                    alumnos.remove(alumno);
                } else {
                    alumnos.put(alumno, alum);
                }

                // Cerrado antes para que sea capaz de reescribirlo
                reader.close();

                final ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(this.getFile(fileName)));
                writer.writeObject(alumnos);
                writer.flush();
                writer.close();
                Log.success("Archivo guardado");
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
    }
}
