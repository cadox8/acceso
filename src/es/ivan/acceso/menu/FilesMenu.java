package es.ivan.acceso.menu;

import es.ivan.acceso.files.PlainFiles;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class FilesMenu {

    private final MainMenu mainMenu;
    private final PlainFiles plainFiles;

    private final Scanner scanner;

    public FilesMenu(MainMenu mainMenu, Scanner scanner) {
        this.mainMenu = mainMenu;
        this.plainFiles = new PlainFiles();

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
                    this.plainFiles.showFileInfo(this.scanner.nextLine());
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

                    this.plainFiles.saveFile(fileName, sb.toString());
                    Log.div();
                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.plainFiles.removeFile(FileType.PLAIN, this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    this.plainFiles.showFileTree(FileType.PLAIN);
                    break;
                case 5:
                    this.mainMenu.showMenu();
                    return;
                default:
                    throw new NoSuchElementException();
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            Log.error("No has pasado un argumento valido");
            Log.clear();
            this.showMenu();
        }
        this.showMenu();
    }
}

