package es.ivan.acceso.menu;

import es.ivan.acceso.files.BinFiles;
import es.ivan.acceso.utils.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BinMenu {

    private final MainMenu mainMenu;
    private final BinFiles binFiles;

    private final Scanner scanner;

    public BinMenu(MainMenu mainMenu, Scanner scanner) {
        this.mainMenu = mainMenu;
        this.binFiles = new BinFiles();

        this.scanner = scanner;
    }

    /**
     * Este método genera y muestra el menú de la aplicación.
     * Se pueden introducir valores del 1-5, cualquier otro valor, letra o palabra será rechazado y se mostrará un aviso al usuario
     */
    public void showMenu() {
        Log.div();
        Log.normal("1. Ver contenido de archivo");
        Log.normal("2. Editar/Borrar alumno");
        Log.normal("3. Añadir alumno");
        Log.normal("4. Borrar archivo");
        Log.normal("5. Ver archivos guardados");
        Log.normal("6. Volver");
        Log.div();

        try {
            switch (Integer.parseInt(this.scanner.nextLine())) {
                case 1:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.binFiles.showFileInfo(scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 2:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    final String fileName = this.scanner.nextLine();

                    this.binFiles.showAlumnos(fileName);

                    Log.normal("Escriba el nombre del alumno a editar:");
                    final String alumno = this.scanner.nextLine();

                    Log.divWithBreak();
                    if (!this.binFiles.showAlumno(fileName, alumno)) break;

                    Log.normal("Seleccione el valor a editar");
                    final int selection = Integer.parseInt(this.scanner.nextLine());

                    Log.normal("Introduzca el nuevo valor [en el caso de aprobado = s/n | en el caso de borrar, doble enter]");
                    this.binFiles.editFile(fileName, alumno, selection, this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    final String fileName2 = this.scanner.nextLine();

                    Log.normal("Escriba el nombre del alumno a editar:");
                    final String alumno2 = this.scanner.nextLine();

                    Log.normal("Escriba la asignatura");
                    final String asignatura = this.scanner.nextLine();

                    Log.normal("Escriba el curso");
                    final String curso = this.scanner.nextLine();

                    Log.normal("Escriba la nota");
                    final String nota = this.scanner.nextLine();

                    Log.normal("Diga si está aprobado [s] o suspenso [n]");
                    final boolean aprobado = this.scanner.nextLine().equalsIgnoreCase("s");

                    this.binFiles.addAlumno(fileName2, alumno2, asignatura, curso, aprobado, nota);
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.binFiles.removeFile(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 5:
                    this.binFiles.showFileTree();
                    break;
                case 6:
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

