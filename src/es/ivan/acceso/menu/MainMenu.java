package es.ivan.acceso.menu;

import es.ivan.acceso.utils.Log;
import es.ivan.acceso.utils.RandomNumbers;

import java.util.*;
import java.util.stream.Collectors;

public class MainMenu {

    private final Scanner scanner;

    private final FilesMenu filesMenu;
    private final PropertiesMenu propertiesMenu;
    private final BinMenu binMenu;
    private final XMLMenu xmlMenu;

    public MainMenu() {
        this.scanner = new Scanner(System.in, "UTF-8");
        this.scanner.useLocale(new Locale("es", "ES"));

        this.filesMenu = new FilesMenu(this, this.scanner);
        this.propertiesMenu = new PropertiesMenu(this, this.scanner);
        this.binMenu = new BinMenu(this, this.scanner);
        this.xmlMenu = new XMLMenu(this, this.scanner);
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
        Log.normal("4. Archivos XML");
        Log.normal("5. Números aleatorios");
        Log.normal("6. Salir");
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
                    this.binMenu.showMenu();
                    break;
                case 4:
                    this.xmlMenu.showMenu();
                    break;
                case 5:
                    Log.normal("Números [10]: " + new RandomNumbers().generateRandomNumbers(10, 100));
                    break;
                case 6:
                    Log.warning("Ta luego pana");
                    System.exit(0);
                    break;
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
