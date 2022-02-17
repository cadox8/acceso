package es.ivan.acceso.ems.paginators;

import es.ivan.acceso.ems.api.Intervention;
import es.ivan.acceso.ems.database.queries.InterventionsQuery;
import es.ivan.acceso.ems.utils.Table;
import es.ivan.acceso.log.Log;

import java.io.Console;
import java.util.Collections;
import java.util.List;

public class InterventionsPaginator {

    private final Console console;

    private final List<Intervention> interventions;
    private final Table table;
    private final InterventionsQuery interventionsQuery;
    private int currentPage;

    public InterventionsPaginator(Console console, List<Intervention> interventions) {
        this.console = console;
        this.interventions = interventions;
        this.table = new Table();

        Log.info("[*] Intervenciones totales: " + this.interventions.size());

        this.currentPage = 0;

        this.interventionsQuery = new InterventionsQuery();

        this.showInterventions();
    }

    public void showIntervention(int id) {
        Log.putBreak(1);

        if (this.interventions.size() < id) {
            Log.normal(this.table.interventionsToTable(Collections.singletonList(this.interventions.get(id - 1))));
        } else {
            Log.error("No hay ninguna intervención con la Id " + id);
            this.showInterventions();
            return;
        }

        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[R] -> Borrar intervención | [X] -> Volver atrás");

        switch (this.console.readLine()) {
            case "r":
                this.interventionsQuery.removeIntervention(id);
                Log.success("La intervención [" + id + "] ha sido eliminado correctamente!");
                break;
            case "x":
                this.showInterventions();
                break;
            default:
                Log.error("Lo sentimos, no sabemos que has introducido");
                this.showIntervention(id);
                break;
        }
    }

    public void next() {
        this.showInterventions();
        this.currentPage++;
        if (this.currentPage * 20 > this.interventions.size()) this.currentPage = 0;
    }

    public void previous() {
        this.showInterventions();
        this.currentPage--;
        if (this.currentPage < 0) this.currentPage = this.interventions.size() / 20;
    }

    private void showInterventions() {
        Log.putBreak(1);
        Log.info("[*] Página " + (this.currentPage + 1) + "/" + (this.interventions.size() / 20 + 1));
        Log.normal(this.table.interventionsToTable(this.interventions.subList(this.currentPage * 20, Math.min(this.interventions.size(), (this.currentPage + 1) * 20))));
        Log.putBreak(1);

        Log.info("Seleccione una de las siguientes opciones:");
        Log.info("[A] -> Intervenciones anteriores | [D] -> Intervenciones siguientes | [<ID>] -> Escriba el Id para ver la información de la intervención | [X] -> Volver atrás");

        final String selection = this.console.readLine();

        try {
            this.showIntervention(Integer.parseInt(selection));
        } catch (NumberFormatException e) {
            switch (selection) {
                case "a":
                    this.previous();
                    break;
                case "d":
                    this.next();
                    break;
                case "x":
                    break;
                default:
                    Log.error("Lo sentimos, no sabemos que has introducido");
                    this.showInterventions();
                    break;
            }
        }
    }
}
