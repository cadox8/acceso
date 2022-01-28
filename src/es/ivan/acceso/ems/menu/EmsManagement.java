package es.ivan.acceso.ems.menu;

import es.ivan.acceso.Acceso;
import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.api.Rank;
import es.ivan.acceso.ems.utils.Table;
import es.ivan.acceso.log.Log;
import lombok.RequiredArgsConstructor;

import java.io.Console;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class EmsManagement {

    private final Ems instance;

    private final Console console;

    public void showMenu() {
        Log.div();
        Log.success("Bienvenido, " + this.instance.getOwn().getName() + ", al servicio de gestión médica de LATAM");
        Log.normal("Versión del sistema: " + Acceso.getVERSION());
        Log.divWithBreakEnd();

        Log.normal("Seleccione donde quiere acceder:");
        Log.normal("[1] Ver pacientes");
        Log.normal("[2] Añadir paciente");
        Log.normal("[3] ????");

        if (this.instance.getOwn().getRank() == Rank.JEFE) {
            Log.info("[*] Gestión administrativa");
            Log.normal("[4] Ver equipo médico");
        }

        Log.info("[*] Gestión propia");
        Log.normal("[5] Ver información propia");
        Log.normal("[6] Desconectarse");
        Log.div();

        try {
            switch (Integer.parseInt(this.console.readLine())) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    Log.normal(Table.toTable(this.instance.getOwn()));
                    break;
                case 6:
                    this.instance.logout();
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
