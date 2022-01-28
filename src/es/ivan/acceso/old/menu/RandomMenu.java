package es.ivan.acceso.old.menu;

import es.ivan.acceso.old.api.Alumno;
import es.ivan.acceso.old.files.RandomFiles;
import es.ivan.acceso.log.Log;
import es.ivan.acceso.old.utils.Normalize;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RandomMenu {

    private final MainMenu mainMenu;
    private final RandomFiles randomFiles;

    private final Scanner scanner;

    public RandomMenu(MainMenu mainMenu, Scanner scanner) {
        this.mainMenu = mainMenu;
        this.randomFiles = new RandomFiles();

        this.scanner = scanner;
    }

    /**
     * Este método genera y muestra el menú de la aplicación.
     * Se pueden introducir valores del 1-5, cualquier otro valor, letra o palabra será rechazado y se mostrará un aviso al usuario
     */
    public void showMenu() {
        Log.div();
        Log.normal("1. Ver contenido de archivo");
        Log.normal("2. Escribir en archivo");
        Log.normal("3. Borrar archivo");
        Log.normal("4. Ver archivos guardados");
        Log.normal("5. Volver");
        Log.div();

        try {
            switch (Integer.parseInt(this.scanner.nextLine())) {
                case 1:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.randomFiles.showFileInfo(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 2:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    final String fileName = this.scanner.nextLine();

                    Log.normal("Escriba el nombre del alumno a añadir:");
                    final String alumno = Normalize.normalizeWord(this.scanner.nextLine());

                    Log.normal("Escriba la asignatura");
                    final String asignatura = Normalize.normalizeWord(this.scanner.nextLine());

                    Log.normal("Escriba el curso");
                    final String curso = Normalize.normalizeWord(this.scanner.nextLine());

                    Log.normal("Escriba la nota");
                    final String nota = this.scanner.nextLine();

                    float parsedNota;

                    try {
                        parsedNota = Float.parseFloat(nota);
                        if (parsedNota < 0 || parsedNota > 10) {
                            parsedNota = 0;
                            Log.error("La nota debe ser entre 0 y 10. Poniendo 0 en su lugar");
                        }
                    } catch (NumberFormatException e) {
                        parsedNota = 0;
                        Log.error("La nota debe ser un número. Poniendo 0 en su lugar");
                        Log.stack(e.getStackTrace());
                    }

                    Log.normal("Diga si está aprobado [s] o suspenso [n]");
                    final boolean aprobado = this.scanner.nextLine().equalsIgnoreCase("s");

                    this.randomFiles.saveFile(fileName, new Alumno(alumno, asignatura, curso, aprobado, parsedNota));
                    Log.div();
                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.randomFiles.removeFile(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    this.randomFiles.showFileTree();
                    break;
                case 5:
                    this.mainMenu.showMenu();
                    return;
                default:
                    throw new NoSuchElementException();
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            Log.clear();
            Log.error("No has pasado un argumento valido");
            Log.stack(e.getStackTrace());
            this.showMenu();
        }
        this.showMenu();
    }
}

