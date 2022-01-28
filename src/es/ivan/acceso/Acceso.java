package es.ivan.acceso;

import es.ivan.acceso.old.menu.MainMenu;
import es.ivan.acceso.log.Log;
import lombok.Getter;

import java.io.*;

public class Acceso {

    @Getter private static String VERSION = "3.0.0-SNAPSHOT";

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
