package es.ivan.acceso.menu;

import es.ivan.acceso.utils.Log;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;

    private final FilesMenu filesMenu;
    private final PropertiesMenu propertiesMenu;

    public MainMenu() {
        this.scanner = new Scanner(System.in);

        this.filesMenu = new FilesMenu(this, this.scanner);
        this.propertiesMenu = new PropertiesMenu(this, this.scanner);
    }

    /**
     * Este método genera y muestra el menú de la aplicación.
     * Se pueden introducir valores del 1-5, cualquier otro valor, letra o palabra será rechazado y se mostrará un aviso al usuario
     */
    public void showMenu() {
        Log.div();
        Log.normal("1. Texto plano");
        Log.normal("2. Archivo de propiedades");
        Log.normal("3. Archivos Binarios");
        Log.normal("4. Salir");
        Log.div();

        try {
            switch (Integer.parseInt(this.scanner.nextLine())) {
                case 1:
                    this.filesMenu.showMenu();
                    return;
                case 2:
                    this.propertiesMenu.showMenu();
                    return;
                case 3:
                    Log.error("Aun no está esto, pana");
                    break;
                case 4:
                    Log.warning("Ta luego pana");
                    System.exit(0);
                    break;
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

    /**
     * Método muy simple para "limpiar" la consola
     */
    private void clearConsole() {
        Log.normal("\n\n\n\n\n\n\n\n\n\n");
    }
}
