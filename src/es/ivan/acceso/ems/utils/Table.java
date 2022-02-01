package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.*;
import es.ivan.acceso.log.Log;

import java.util.*;

public class Table {

    // --- General ---
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
        } else {

        }
        // --- ---

        // --- Format Length ---
        for (int i = 0; i < headers.size(); i++) {
            final int maxLength = Math.max(headers.get(i).length(), this.maxLength(body, i));
            headers.set(i, this.fixLength(headers.get(i), maxLength));
            int finalI = i;
            body.values().forEach(l -> this.fixLength(l.get(finalI), maxLength));
        }
        // --- ---

        final StringBuilder sb = new StringBuilder();
        sb.append(this.formatList(headers));
        sb.append("\n");
        body.forEach((id, list) -> sb.append(this.formatList(list)).append("\n"));

        return sb.toString();
    }

    // --- Utils ---
    private String formatList(final List<String> list) {
        return list.toString().replaceAll("\\[", "| ").replaceAll("]", " |").replaceAll(",", " |");
    }

    private String fixLength(String text, int length) {
        final StringBuilder textBuilder = new StringBuilder(text);
        for (int i = text.length(); i <= length; i++) textBuilder.append(" ");
        return textBuilder.toString();
    }

    private int maxLength(HashMap<Integer, List<String>> body, int search) {
        final Integer[] lengths = new Integer[body.keySet().size()];
        body.forEach((id, content) -> lengths[id] = content.get(search).length());
        Arrays.sort(lengths, (l1, l2) -> l1 > l2 ? 1 : -1);
        return lengths[0];
    }

    public enum TableType {
        MEDIC, PATIENT
    }
}
