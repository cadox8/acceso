package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.ems.database.queries.PatientQuery;
import es.ivan.acceso.log.Log;
import lombok.RequiredArgsConstructor;

import java.io.Console;
import java.util.Collections;

@RequiredArgsConstructor
public class AddPatient {

    private final Console console;

    private Patient patient;

    private boolean update;

    public void start() {
        this.patient = new Patient();
        this.patient.setId(-1);
        this.update = false;
        this.askName(true);
    }

    public void startFromPatient(Patient patient) {
        this.patient = patient;
        this.update = true;
        this.check();
    }

    private void askName(boolean next) {
        Log.normal("Por favor, introduzca el nombre del paciente:");
        this.patient.setName(this.console.readLine());
        if (next) this.askPhone(next);
    }

    private void askPhone(boolean next) {
        Log.normal("Por favor, introduzca el teléfono del paciente:");
        this.patient.setPhone(this.console.readLine());
        if (next) this.askAge(next);
    }

    private void askAge(boolean next) {
        Log.normal("Por favor, introduzca la edad del paciente:");
        try {
            this.patient.setAge(Integer.parseInt(this.console.readLine()));
        } catch (NumberFormatException e) {
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.askAge(next);
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
        Log.normal(new Table().patientsToTable(Collections.singletonList(this.patient)));
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
                    this.askAge(false);
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
                final PatientQuery patientQuery = new PatientQuery();

                if (this.update) {
                    if (patientQuery.updatePatient(this.patient)) {
                        Log.success("Paciente actualizado");
                    } else {
                        Log.error("Ha ocurrido un error al actualizar al paciente.");
                    }
                } else {
                    if (patientQuery.addPatient(this.patient)) {
                        Log.success("Paciente guardado");
                    } else {
                        Log.error("Ha ocurrido un error al guardar al paciente.");
                    }
                }
                return;
            }
            Log.stack(e.getStackTrace());
            Log.error("Has puesto un número erroneo");
            this.check();
        }
    }
}
