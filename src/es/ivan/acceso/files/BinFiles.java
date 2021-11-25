package es.ivan.acceso.files;

import es.ivan.acceso.api.Alumno;
import es.ivan.acceso.api.BinParser;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;
import es.ivan.acceso.utils.Normalize;

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
                this.readObject(fileName).forEach((name, alum) -> {
                    Log.normal(name + ":");
                    this.showAlumnoInfo(alum, false);
                });
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
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
                this.readObject(fileName).keySet().forEach(Log::normal);
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
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
                final HashMap<String, Alumno> alumnos = this.readObject(fileName);
                alumno = Normalize.normalizeWord(alumno);

                if (this.existsAlumno(alumnos, alumno)) {
                    this.showAlumnoInfo(alumnos.get(alumno), true);
                    return true;
                }
                return false;
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
        return false;
    }


    public void addAlumno(String fileName, String alumno, String asignatura, String curso, boolean aprobado, String nota) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final HashMap<String, Alumno> alumnos = this.readObject(fileName);

                alumno = Normalize.normalizeWord(alumno);
                if (alumnos.containsKey(alumno)) {
                    Log.error("Ya existe este alumno. ¿No querrás editarlo, cruck?");
                    return;
                }

                final Alumno alum = new Alumno(alumno, asignatura, curso, aprobado, 0f);

                try {
                    alum.setNota(Float.parseFloat(nota));
                } catch (NumberFormatException e) {
                    alum.setNota(0f);
                    Log.error("Nota inválida, 0 por defecto");
                    Log.stack(e.getStackTrace());
                }

                if (Integer.parseInt(nota) < 0 || Integer.parseInt(nota) > 10) {
                    alum.setNota(0f);
                    Log.error(nota + " > 10 ó < 0. Poniendo 0 por defecto");
                }

                alumnos.put(alum.getNombre(), alum);

                final ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(this.getFile(fileName)));
                writer.writeObject(alumnos);
                writer.flush();
                writer.close();
                Log.success("Alumno añadido!");
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
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
                final HashMap<String, Alumno> alumnos = this.readObject(fileName);

                alumno = Normalize.normalizeWord(alumno);
                if (!this.existsAlumno(alumnos, alumno)) return;

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
                        return;
                }

                if (selection == 6) {
                    alumnos.remove(alumno);
                } else {
                    alumnos.put(alumno, alum);
                }

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

    private HashMap<String, Alumno> readObject(String fileName) throws IOException, ClassNotFoundException {
        final ObjectInputStream reader = new BinParser(new FileInputStream(this.getFile(fileName)), "dam.AD", "es.ivan.acceso.api");

        final HashMap<?, ?> alumnos = (HashMap<?, ?>) reader.readObject();
        final HashMap<String, Alumno> realAlumnos = new HashMap<>();

        alumnos.forEach((key, value) -> realAlumnos.put(Normalize.normalizeWord((String) key), (Alumno) value));

        reader.close();
        return realAlumnos;
    }

    private boolean existsAlumno(HashMap<String, Alumno> alumnos, String alumno) {
        if (!alumnos.containsKey(alumno)) {
            Log.error("No existe el alumno " + alumno);
            return false;
        }
        return true;
    }

    private void showAlumnoInfo(Alumno alumno, boolean deleteOption) {
        Log.normal(alumno + ":");
        final StringBuilder sb = new StringBuilder();
        sb.append("  1. Nombre: " + Normalize.normalizeWord(alumno.getNombre()));
        sb.append("\n  2. Asignatura: " + Normalize.normalizeWord(alumno.getAsignatura()));
        sb.append("\n  3. Curso: " + Normalize.normalizeWord(alumno.getCurso()));
        sb.append("\n  4. Aprobado: " + (alumno.getAprobado() ? "Sí" : "No"));
        sb.append("\n  5. Nota: " + alumno.getNota());

        if (deleteOption) sb.append("\n  6. Borrar");

        sb.append('\n');
        Log.normal(sb.toString());
    }
}
