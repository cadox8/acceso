package es.ivan.acceso;

import es.ivan.acceso.log.Log;
import es.ivan.acceso.old.menu.MainMenu;
import lombok.Getter;

public class Acceso {

    @Getter
    private static final String VERSION = "3.1.0";

    /**
     * Main class & method to start the program
     *
     * @param args Default args
     */
    public static void main(String... args) {
        Log.createLogFile();

        // --- Basic information ---
        Log.divWithBreak();
        Log.success("Acceso iniciado. Versión: " + VERSION);
        Log.divWithBreakEnd();
        // --- ---

        new MainMenu().showMenu();
    }
}
