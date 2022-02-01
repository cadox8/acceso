package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.log.Log;

import java.io.Console;
import java.util.Collections;
import java.util.List;

public class PatientsPaginator {

    private final Console console;

    private final List<Patient> patients;
    private final Table table;

    private int currentPage;

    public PatientsPaginator(Console console, List<Patient> patients) {
        this.console = console;
        this.patients = patients;
        this.table = new Table();

        Log.info("[*] Pacientes totales: " + this.patients.size());

        this.currentPage = 0;
        this.showPatients();
    }

    public void showPatient(int id) {
        this.table.toTable(Collections.singletonList(this.patients.get(id - 1)), Table.TableType.PATIENT);
    }

    public void next() {
        this.showPatients();
        this.currentPage++;
        if (this.currentPage * 20 > this.patients.size()) this.currentPage = 0;
    }

    public void previous() {
        this.showPatients();
        this.currentPage--;
        if (this.currentPage < 0) this.currentPage = this.patients.size() / 20;
    }

    private void showPatients() {
        Log.putBreak(1);
        Log.info("[*] Página " + (this.currentPage + 1) + "/" + (this.patients.size() / 20 + 1));
        this.table.toTable(this.patients.subList(this.currentPage * 20, 20), Table.TableType.PATIENT);
        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[A] -> Pacientes anteriores | [D] -> Pacientes siguientes | [<ID>] -> Escriba el Id para ver la información del paciente | [X] -> Volver atrás");

        try {
            this.showPatient(Integer.parseInt(this.console.readLine()));
        } catch (NumberFormatException e) {
            switch (this.console.readLine().toLowerCase()) {
                case "a":
                    this.previous();
                    break;
                case "d":
                    this.next();
                    break;
                case "x":
                    return;
                default:
                    Log.error("Lo sentimos, no sabemos que has introducido");
                    this.showPatients();
                    break;
            }
        }
    }
}
