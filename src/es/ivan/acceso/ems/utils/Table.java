package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.*;
import es.ivan.acceso.log.Log;

import java.util.*;

public class Table {

    /**
     * Método para generar una tabla (con cabecera) a partir de una lista de objetos
     *
     * @param medics Los médicos
     * @return Un String con el valor de los objetos formateados como tabla
     */
    public String medicsToTable(List<Medic> medics) {
        return this.toTable(medics, TableType.MEDIC);
    }

    /**
     * Método para generar una tabla (con cabecera) a partir de una lista de objetos
     *
     * @param interventions Las intervenciones
     * @return Un String con el valor de los objetos formateados como tabla
     */
    public String interventionsToTable(List<Interventions> interventions) {
        return this.toTable(interventions, TableType.INTERVENTIONS);
    }

    /**
     * Método para generar una tabla (con cabecera) a partir de una lista de objetos
     *
     * @param patients Los pacientes
     * @return Un String con el valor de los objetos formateados como tabla
     */
    public String patientsToTable(List<Patient> patients) {
        return this.toTable(patients, TableType.PATIENT);
    }

    /**
     * Método para generar una tabla (con cabecera) a partir de una lista de objetos
     *
     * @param objects Los objetos que queremos mostrar como tabla
     * @param type El tipo de objeto que le estamos pasando
     * @return Un String con el valor de los objetos formateados como tabla
     */
    public String toTable(List<? extends AbstractAPI> objects, TableType type) {
        final List<String> headers = new ArrayList<>();
        final HashMap<Integer, List<String>> body = new HashMap<>();

        if (objects.isEmpty()) {
            Log.warning("No hay datos guardados");
            return "";
        }

        // --- Set headers ---
        Arrays.asList(objects.get(0).getClass().getDeclaredFields()).forEach(f -> headers.add(f.getAnnotation(LocalizeName.class).value()));
        // --- ---

        // --- Set body ---
        for (int i = 0; i < objects.size(); i++) {
            int finalI = i;
            final List<String> tempBody = new ArrayList<>();
            Arrays.asList(objects.get(i).getClass().getDeclaredFields()).forEach(f -> {
                f.setAccessible(true);
                try {
                    tempBody.add(String.valueOf(f.get(objects.get(finalI))));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                f.setAccessible(false);
            });
            body.put(i, tempBody);
        }
        // --- ---

        // --- Format fields ---
        if (type == TableType.MEDIC) {
            body.forEach((id, list) -> {
                for (int i = 0; i < list.size(); i++) {
                    switch (i) {
                        case 3:
                            list.set(i, Rank.parseRank(Integer.parseInt(list.get(i))).getDisplay());
                            break;
                        case 4:
                        case 5:
                            list.set(i, list.get(i).equalsIgnoreCase("1") ? "Sí" : "No");
                            break;
                    }
                }
            });
        }
        // --- ---

        // --- Format Length ---
        for (int i = 0; i < headers.size(); i++) {
            final int maxLength = Math.max(headers.get(i).length(), this.maxLength(body, i));
            headers.set(i, this.fixLength(headers.get(i), maxLength));
            int finalI = i;

            body.forEach((id, list) -> {
                list.set(finalI, this.fixLength(list.get(finalI), maxLength));
                body.put(id, list);
            });
        }
        // --- ---

        final StringBuilder sb = new StringBuilder();
        sb.append(this.formatList(headers));
        sb.append("\n");
        body.forEach((id, list) -> sb.append(this.formatList(list)).append("\n"));

        return sb.toString();
    }

    // --- Utils ---

    /**
     * Método que devuelve el contenido de una lista con un formato de tabla
     *
     * @param list La lista
     * @return El contenido de la lista como tabla
     */
    private String formatList(final List<String> list) {
        return list.toString().replaceAll("\\[", "| ").replaceAll("]", " |").replaceAll(",", " |");
    }

    /**
     * Método para ajustar la longitud de cada campo al valor máximo
     *
     * @param text El texto para ajustar
     * @param length La longitud final del texto a ajustar
     * @return El texto ajustado a la longitud
     */
    private String fixLength(String text, int length) {
        final StringBuilder textBuilder = new StringBuilder(text);
        for (int i = text.length(); i <= length; i++) textBuilder.append(" ");
        return textBuilder.toString();
    }

    /**
     * Método para calcular la máxima longitud de cada campo de la lista con respecto a todos los campos de todas las listas del HashMap
     *
     * @param body El HashMap con todas las listas
     * @param search El índice de la lista que estamos comparando
     * @return La máxima longitud de cada campo de la lista con respecto a todos los campos de todas las listas del HashMap
     */
    private int maxLength(HashMap<Integer, List<String>> body, int search) {
        final Integer[] lengths = new Integer[body.keySet().size()];
        body.forEach((id, content) -> lengths[id] = content.get(search).length());
        Arrays.sort(lengths, Comparator.naturalOrder());
        return lengths[0];
    }

    public enum TableType {
        MEDIC, PATIENT, INTERVENTIONS
    }
}
