package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.ems.database.queries.MedicQuery;
import es.ivan.acceso.ems.database.queries.PatientQuery;
import es.ivan.acceso.log.Log;
import lombok.RequiredArgsConstructor;

import java.io.Console;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
public class AddMedic {

    private final Console console;

    private Medic medic;

    public void start() {
        this.medic = new Medic();
        this.medic.setId(-1);
        this.medic.setAdmin(0);
        this.medic.setActive(1);
        this.askName(true);
    }

    private void askName(boolean next) {
        Log.normal("Por favor, introduzca el nombre del médico:");
        this.medic.setName(this.console.readLine());
        if (next) this.askUsername(next);
    }

    private void askUsername(boolean next) {
        Log.normal("Por favor, introduzca el usuario del médico:");
        this.medic.setUsername(this.console.readLine());
        if (next) this.askRank(next);
    }

    private void askRank(boolean next) {
        Log.normal("Por favor, introduzca el rango del médico [0-5]:");
        try {
            final int rank = Integer.parseInt(this.console.readLine());
            if (rank < 0 || rank > 5) throw new NumberFormatException();
            this.medic.setRank(rank);
        } catch (NumberFormatException e) {
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.askRank(next);
        }
        if (next) this.check();
    }

    private void check() {
        Log.putBreak(1);
        Log.normal(new Table().medicsToTable(Collections.singletonList(this.medic)));
        Log.putBreak(1);
        Log.info("[*] Revisa si los datos son correctos");
        Log.normal("[1-3] -> Editar valores | [X] -> Guardar Paciente");

        final String selection = this.console.readLine();

        try {
            switch (Integer.parseInt(selection)) {
                case 1:
                    this.askName(false);
                    break;
                case 2:
                    this.askUsername(false);
                    break;
                case 3:
                    this.askRank(false);
                    break;
            }
            this.check();
        } catch (NumberFormatException e) {
            if (selection.equalsIgnoreCase("x")) {
                if (new MedicQuery().addMedic(this.medic)) {
                    Log.success("Médico guardado");
                } else {
                    Log.error("Ha ocurrido un error al guardar al médico.");
                }
                return;
            }
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.check();
        }
    }
}
