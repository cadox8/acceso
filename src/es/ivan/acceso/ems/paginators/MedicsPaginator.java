package es.ivan.acceso.ems.paginators;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.database.queries.MedicQuery;
import es.ivan.acceso.ems.utils.AddMedic;
import es.ivan.acceso.ems.utils.Table;
import es.ivan.acceso.log.Log;

import java.io.Console;
import java.util.Collections;
import java.util.List;

public class MedicsPaginator {

    private final Console console;

    private final List<Medic> medics;
    private final Table table;
    private final MedicQuery medicQuery;
    private int currentPage;

    public MedicsPaginator(Console console, List<Medic> medics) {
        this.console = console;
        this.medics = medics;
        this.table = new Table();

        Log.info("[*] Médicos totales: " + this.medics.size());

        this.currentPage = 0;

        this.medicQuery = new MedicQuery();

        this.showMedics();
    }

    public void showMedic(int id) {
        Log.putBreak(1);

        if (this.medics.stream().anyMatch(p -> p.getId() == id)) {
            Log.normal(this.table.medicsToTable(Collections.singletonList(this.medics.get(id - 1))));
        } else {
            Log.error("No hay ningún médico con la Id " + id);
            this.showMedics();
            return;
        }

        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[E] -> Editar médico | [R] -> Borrar médico | [X] -> Volver atrás");

        switch (this.console.readLine()) {
            case "e":
                new AddMedic(this.console).startFromMedic(this.medics.get(id - 1));
                break;
            case "r":
                this.medicQuery.removeMedic(id);
                Log.success("El médico [" + id + "] " + this.medics.get(id - 1).getName() + " ha sido eliminado correctamente!");
                break;
            case "x":
                this.showMedics();
                return;
            default:
                Log.error("Lo sentimos, no sabemos que has introducido");
                this.showMedic(id);
                break;
        }
    }

    public void next() {
        this.showMedics();
        this.currentPage++;
        if (this.currentPage * 20 > this.medics.size()) this.currentPage = 0;
    }

    public void previous() {
        this.showMedics();
        this.currentPage--;
        if (this.currentPage < 0) this.currentPage = this.medics.size() / 20;
    }

    private void showMedics() {
        Log.putBreak(1);
        Log.normal(this.table.medicsToTable(this.medics.subList(this.currentPage * 20, Math.min(this.medics.size(), (this.currentPage + 1) * 20))));
        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[A] -> Médicos anteriores | [D] -> Médicos siguientes | [N] -> Nuevo médico |  [<ID>] -> Escriba el Id para ver la información del médico | [X] -> Volver atrás");

        final String selection = this.console.readLine();

        try {
            this.showMedic(Integer.parseInt(selection));
        } catch (NumberFormatException e) {
            switch (selection) {
                case "a":
                    this.previous();
                    break;
                case "d":
                    this.next();
                    break;
                case "n":
                    Log.putBreak(1);
                    new AddMedic(this.console).start();
                    Log.putBreak(1);
                    break;
                case "x":
                    return;
                default:
                    Log.error("Lo sentimos, no sabemos que has introducido");
                    this.showMedics();
                    break;
            }
        }
    }
}
