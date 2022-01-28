package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.Medic;

import java.util.Arrays;

public class Table {

    public String toTable(Medic medic) {
        final StringBuilder sb = new StringBuilder("| ");

        // --- Set headers ---
        Arrays.asList(medic.getClass().getDeclaredFields()).forEach(f -> sb.append(f.getName()).append(" | "));
        // --- ---

        sb.append("\n");


        return sb.toString();
    }

    private String centerText(String content) {

    }
}
