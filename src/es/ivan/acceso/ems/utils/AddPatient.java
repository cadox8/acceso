package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.ems.database.queries.PatientQuery;
import es.ivan.acceso.log.Log;
import lombok.RequiredArgsConstructor;

import java.io.Console;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
public class AddPatient {

    private final Console console;

    private Patient patient;

    public void start() {
        this.patient = new Patient();
        this.askName(true);
    }

    private void askName(boolean next) {
        Log.normal("Por favor, introduzca el nombre del paciente:");
        this.patient.setName(this.console.readLine());
        if (next) this.askPhone(next);
    }

    private void askPhone(boolean next) {
        Log.normal("Por favor, introduzca el teléfono del paciente:");
        this.patient.setPhone(this.console.readLine());
        if (next) this.askDob(next);
    }

    private void askDob(boolean next) {
        Log.normal("Por favor, introduzca la fecha de nacimiento del paciente: [DD/MM/AAAA]");
        final String[] parts = this.console.readLine().split("/");
        try {
            this.patient.setDob(new Date(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]) + 1, Integer.parseInt(parts[0])));
        } catch (NumberFormatException e) {
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.askDob(next);
        }
        if (next) this.askHeight(next);
    }

    private void askHeight(boolean next) {
        Log.normal("Por favor, introduzca la altura del paciente (en metros):");
        try {
            this.patient.setHeight(Double.parseDouble(this.console.readLine()));
        } catch (NumberFormatException e) {
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.askHeight(next);
        }
        if (next) this.askWeight(next);
    }

    private void askWeight(boolean next) {
        Log.normal("Por favor, introduzca el peso del paciente (en kg):");
        try {
            this.patient.setWeight(Double.parseDouble(this.console.readLine()));
        } catch (NumberFormatException e) {
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.askHeight(next);
        }
        if (next) this.askBlood(next);
    }

    private void askBlood(boolean next) {
        Log.normal("Por favor, introduzca el tipo de sangre del paciente:");
        this.patient.setBlood(this.console.readLine());
        if (next) this.check();
    }

    private void check() {
        Log.putBreak(1);
        new Table().toTable(Collections.singletonList(this.patient), Table.TableType.PATIENT);
        Log.putBreak(1);
        Log.info("[*] Revisa si los datos son correctos");
        Log.normal("[1-6] -> Editar valores | [X] -> Guardar Paciente");

        final String selection = this.console.readLine();

        try {
            switch (Integer.parseInt(selection)) {
                case 1:
                    this.askName(false);
                    break;
                case 2:
                    this.askPhone(false);
                    break;
                case 3:
                    this.askDob(false);
                    break;
                case 4:
                    this.askHeight(false);
                    break;
                case 5:
                    this.askWeight(false);
                    break;
                case 6:
                    this.askBlood(false);
                    break;
            }
            this.check();
        } catch (NumberFormatException e) {
            if (selection.equalsIgnoreCase("x")) {
                new PatientQuery().addPatient(this.patient);
                Log.success("Paciente guardado");
                return;
            }
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.check();
        }
    }
}
