package es.ivan.acceso.menu;

import es.ivan.acceso.files.PropertiesFiles;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;

import java.util.*;

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
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.propertiesFiles.showFileInfo(this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 2:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    final String fileName = this.scanner.nextLine();
                    Log.normal("Escribe el contenido [Formato key=value] (Dar doble enter para finalizar):");

                    final HashMap<String, String> properties = new HashMap<>();

                    //Lee la consola hasta que se inserte una linea en blanco
                    while (this.scanner.hasNextLine()) {
                        final String line = this.scanner.nextLine();
                        if (line.isEmpty()) break;

                        if (line.contains("=")) {
                            final String[] parts = line.split("=");

                            if (parts[0].isEmpty() || parts[1].isEmpty()) {
                                Log.error("Revisa el key y el value, parece que uno está vacios");
                            } else {
                                properties.put(parts[0], parts[1]);
                            }

                        } else {
                            Log.error("El formato debe ser key=value, cruck");
                        }
                    }

                    this.propertiesFiles.saveFile(fileName, properties);
                    Log.div();
                    this.showMenu();
                    break;
                case 3:
                    Log.divWithBreak();
                    Log.normal("Escriba el nombre del archivo:");
                    this.propertiesFiles.removeFile(FileType.PROP, this.scanner.nextLine());
                    Log.div();
                    this.showMenu();
                    break;
                case 4:
                    this.propertiesFiles.showFileTree(FileType.PROP);
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
