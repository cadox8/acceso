package es.ivan.acceso.old.files;

import es.ivan.acceso.log.Log;
import es.ivan.acceso.old.api.Alumno;
import es.ivan.acceso.old.api.BinParser;
import es.ivan.acceso.old.files.type.FileType;
import es.ivan.acceso.old.utils.Normalize;

import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;

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
                this.readObject(fileName).forEach(alum -> this.showAlumnoInfo(alum, false));
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
                this.readObject(fileName).forEach(a -> Log.normal(a.getNombre()));
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
     * @param alumno   El alumno a ser buscado
     * @return True if the alumno exists
     */
    public boolean showAlumno(String fileName, String alumno) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final TreeSet<Alumno> alumnos = this.readObject(fileName);
                alumno = Normalize.normalizeWord(alumno);

                if (this.existsAlumno(alumnos, alumno)) {
                    this.showAlumnoInfo(getAlumno(alumnos, alumno), true);
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
                final TreeSet<Alumno> alumnos = this.readObject(fileName);

                alumno = Normalize.normalizeWord(alumno);

                // Creamos la variable porque los valores a pasar en una lambda deben ser final o "final" (que no debe cambiar)
                final String alumnoNombre = alumno;

                if (alumnos.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(alumnoNombre))) {
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

                alumnos.add(alum);

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
     * @param fileName  El archivo donde guardar el contenido
     * @param alumno    La alumno a editar
     * @param selection La selección de que editar
     * @param value     El valor editado
     */
    public void editFile(String fileName, String alumno, int selection, String value) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final TreeSet<Alumno> alumnos = this.readObject(fileName);

                alumno = Normalize.normalizeWord(alumno);
                if (!this.existsAlumno(alumnos, alumno)) return;

                final Alumno alum = getAlumno(alumnos, alumno);

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
                    alumnos.remove(alum);
                } else {
                    alumnos.add(alum);
                }

                final ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(this.getFile(fileName)));

                final HashMap<String, Alumno> alumnosMap = new HashMap<>();

                alumnos.forEach(a -> alumnosMap.put(a.getNombre(), a));

                writer.writeObject(alumnosMap);
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

    public void showDataOrder(String fileName, String orderBy) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                this.readObject(fileName).stream().sorted((a1, a2) -> {
                    switch (orderBy.toLowerCase()) {
                        case "nota":
                            //
                            final Float notaA1 = a1.getNota() == null ? 0.0F : a1.getNota();
                            final Float notaA2 = a2.getNota() == null ? 0.0F : a2.getNota();
                            return notaA2.compareTo(notaA1);
                        case "asignatura":
                            return a1.getAsignatura().compareTo(a2.getAsignatura());
                        case "curso":
                            return a1.getCurso().compareTo(a2.getCurso());
                        case "aprobado":
                            return a2.getAprobado().compareTo(a1.getAprobado());

                        default:
                            return a1.compareTo(a2);
                    }
                }).forEach(a -> this.showAlumnoInfo(a, false));
            } catch (IOException | ClassNotFoundException e) {
                Log.error("Ha ocurrido un error inesperado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".bin");
        }
    }

    private TreeSet<Alumno> readObject(String fileName) throws IOException, ClassNotFoundException {
        final ObjectInputStream reader = new BinParser(new FileInputStream(this.getFile(fileName)), "dam.AD", "es.ivan.acceso.old.api");

        final HashMap<?, ?> alumnos = (HashMap<?, ?>) reader.readObject();
        final HashMap<String, Alumno> realAlumnos = new HashMap<>();

        alumnos.forEach((key, value) -> realAlumnos.put(Normalize.normalizeWord((String) key), (Alumno) value));

        reader.close();
        return new TreeSet<>(realAlumnos.values());
    }

    private boolean existsAlumno(TreeSet<Alumno> alumnos, String alumno) {
        if (alumnos.stream().noneMatch(a -> a.getNombre().equalsIgnoreCase(alumno))) {
            Log.error("No existe el alumno " + alumno);
            return false;
        }
        return true;
    }

    private Alumno getAlumno(TreeSet<Alumno> alumnos, String alumno) {
        return alumnos.stream().filter(a -> a.getNombre().equalsIgnoreCase(alumno)).findAny().orElse(null);
    }

    private void showAlumnoInfo(Alumno alumno, boolean deleteOption) {
        Log.normal(alumno.getNombre() + ":");
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
