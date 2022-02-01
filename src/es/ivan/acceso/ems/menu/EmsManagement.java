package es.ivan.acceso.ems.menu;

import es.ivan.acceso.Acceso;
import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.api.Rank;
import es.ivan.acceso.ems.database.queries.MedicQuery;
import es.ivan.acceso.ems.database.queries.PatientQuery;
import es.ivan.acceso.ems.utils.AddPatient;
import es.ivan.acceso.ems.utils.PatientsPaginator;
import es.ivan.acceso.ems.utils.Table;
import es.ivan.acceso.log.Log;
import lombok.RequiredArgsConstructor;

import java.io.Console;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class EmsManagement {

    private final Ems instance;

    private final Console console;
    private final Table table = new Table();

    public void showMenu() {
        Log.div();
        Log.success("Bienvenido, " + this.instance.getOwn().getName() + ", al servicio de gestión médica de LATAM");
        Log.normal("Versión del sistema: " + Acceso.getVERSION());
        Log.divWithBreakEnd();

        Log.normal("Seleccione donde quiere acceder:");
        Log.info("[*] Consulta general");
        Log.normal("  [1] Ver pacientes");
        Log.normal("  [2] Añadir paciente");
        Log.normal("  [3] ????");
        Log.putBreak(1);

        if (this.instance.getOwn().getRank() == Rank.JEFE) {
            Log.info("[*] Gestión administrativa");
            Log.normal("  [4] Ver equipo médico");
            Log.putBreak(1);
        }

        Log.info("[*] Gestión propia");
        Log.normal("  [5] Ver información propia");
        Log.normal("  [6] Desconectarse");
        Log.div();

        try {
            switch (Integer.parseInt(this.console.readLine())) {
                case 1:
                    Log.putBreak(1);
                    new PatientsPaginator(this.console, new PatientQuery().getPatients());
                    Log.putBreak(1);
                    break;
                case 2:
                    Log.putBreak(1);
                    new AddPatient(this.console).start();
                    Log.putBreak(1);
                    break;
                case 3:

                    break;
                case 4:
                    if (this.instance.getOwn().getRank() != Rank.JEFE) throw new NoSuchElementException();

                    Log.putBreak(1);
                    Log.normal(this.table.toTable(new MedicQuery().getAllMedics(), Table.TableType.MEDIC));
                    Log.putBreak(1);

                    break;
                case 5:
                    Log.putBreak(1);
                    Log.normal(this.table.toTable(Collections.singletonList(this.instance.getOwn()), Table.TableType.MEDIC));
                    Log.putBreak(1);
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
        Log.normal("Pulse cualquier tecla para continuar...");
        this.console.readLine();
        this.showMenu();
    }
}
