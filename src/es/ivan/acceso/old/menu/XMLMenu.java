package es.ivan.acceso.old.menu;

import es.ivan.acceso.log.Log;
import es.ivan.acceso.old.files.XMLFiles;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class XMLMenu {

    private final MainMenu mainMenu;
    private final XMLFiles xmlFiles;

    private final Scanner scanner;

    public XMLMenu(MainMenu mainMenu, Scanner scanner) {
        this.mainMenu = mainMenu;
        this.xmlFiles = new XMLFiles();

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
                    this.xmlFiles.showFileInfo(scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 2:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    final String fileName = this.scanner.nextLine();
                    Log.normal("Escribe el nodo principal");
                    final String node = this.scanner.nextLine();
                    this.xmlFiles.writeNewXML(fileName, node, this.scanner);
                    Log.div();
                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.xmlFiles.removeFile(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    this.xmlFiles.showFileTree();
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

