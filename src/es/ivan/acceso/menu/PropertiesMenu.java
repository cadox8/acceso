package es.ivan.acceso.menu;

import es.ivan.acceso.files.PropertiesFiles;
import es.ivan.acceso.utils.Log;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PropertiesMenu {

    private final MainMenu mainMenu;
    private final PropertiesFiles propertiesFiles;

    private final Scanner scanner;

    public PropertiesMenu(MainMenu mainMenu, Scanner scanner) {
        this.mainMenu = mainMenu;
        this.propertiesFiles = new PropertiesFiles();

        this.scanner = scanner;
    }

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

                    this.showMenu();
                    break;
                case 2:

                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.propertiesFiles.removeFile(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    this.propertiesFiles.showFileTree();
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
