package es.ivan.acceso;

import es.ivan.acceso.menu.MainMenu;
import es.ivan.acceso.utils.Log;

public class Acceso {

    /**
     * Main class & method to start the program
     *
     * @param args Default args
     */
    public static void main(String... args) {
        Log.createLogFile();
        new MainMenu().showMenu();
    }
}
