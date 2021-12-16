package es.ivan.acceso.menu;

import es.ivan.acceso.files.PlainFiles;
import es.ivan.acceso.files.RandomFiles;
import es.ivan.acceso.utils.Log;

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
                    Log.normal("Escribe el contenido (Dar doble enter para finalizar):");

                    //Lee la consola hasta que se inserte una linea en blanco
                    final StringBuilder sb = new StringBuilder();
                    while (this.scanner.hasNextLine()) {
                        final String line = this.scanner.nextLine();
                        if (line.isEmpty()) break;
                        sb.append(line);
                    }

                    this.randomFiles.saveFile(fileName, sb.toString());
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

