package es.ivan.acceso.ems.paginators;

import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.api.Intervention;
import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.ems.database.queries.InterventionsQuery;
import es.ivan.acceso.ems.database.queries.PatientQuery;
import es.ivan.acceso.ems.utils.AddPatient;
import es.ivan.acceso.ems.utils.Table;
import es.ivan.acceso.log.Log;

import java.io.Console;
import java.util.Collections;
import java.util.List;

public class PatientsPaginator {

    private final Console console;

    private final List<Patient> patients;
    private final Table table;

    private int currentPage;

    private final PatientQuery patientQuery;

    public PatientsPaginator(Console console, List<Patient> patients) {
        this.console = console;
        this.patients = patients;
        this.table = new Table();

        Log.info("[*] Pacientes totales: " + this.patients.size());

        this.currentPage = 0;

        this.patientQuery = new PatientQuery();

        this.showPatients();
    }

    public void showPatient(int id) {
        Log.putBreak(1);

        if (this.patients.stream().anyMatch(p -> p.getId() == id)) {
            Log.normal(this.table.patientsToTable(Collections.singletonList(this.patients.get(id - 1))));
        } else {
            Log.error("No hay ningún paciente con la Id " + id);
            this.showPatients();
            return;
        }

        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[A] -> Añadir intervención | [V] -> Ver intervenciones | [E] -> Editar paciente | [R] -> Borrar paciente | [X] -> Volver atrás");

        switch (this.console.readLine()) {
            case "a":
                Log.putBreak(1);
                Log.normal("Escriba todo el proceso de intervención:");
                if (new InterventionsQuery().addIntervention(new Intervention(-1, Ems.getInstance().getOwn(), this.patients.get(id - 1), this.console.readLine(), null))) {
                    Log.success("Intervención guardada");
                } else {
                    Log.error("Ha ocurrido un error al guardar la intervención.");
                }
                Log.putBreak(1);
                break;
            case "e":
                new AddPatient(this.console).startFromPatient(this.patients.get(id - 1));
                break;
            case "v":
                new InterventionsPaginator(this.console, new InterventionsQuery().getAllInterventionsFromPatient(id));
                break;
            case "r":
                this.patientQuery.removePatient(id);
                Log.success("El paciente [" + id + "] " + this.patients.get(id - 1).getName() + " ha sido eliminado correctamente!");
                break;
            case "x":
                this.showPatients();
                return;
            default:
                Log.error("Lo sentimos, no sabemos que has introducido");
                this.showPatient(id);
                break;
        }
    }

    public void next() {
        this.currentPage++;
        if (this.currentPage * 20 > this.patients.size()) this.currentPage = 0;
        this.showPatients();
    }

    public void previous() {
        this.currentPage--;
        if (this.currentPage < 0) this.currentPage = this.patients.size() / 20;
        this.showPatients();
    }

    private void showPatients() {
        Log.putBreak(1);
        Log.info("[*] Página " + (this.currentPage + 1) + "/" + (this.patients.size() / 20 + 1));
        Log.normal(this.table.patientsToTable(this.patients.subList(this.currentPage * 20, Math.min(this.patients.size(), (this.currentPage + 1) * 20))));
        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[A] -> Pacientes anteriores | [D] -> Pacientes siguientes | [<ID>] -> Escriba el Id para ver la información del paciente | [X] -> Volver atrás");

        final String selection = this.console.readLine();

        try {
            this.showPatient(Integer.parseInt(selection));
        } catch (NumberFormatException e) {
            switch (selection) {
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
