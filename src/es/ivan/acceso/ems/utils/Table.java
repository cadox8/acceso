package es.ivan.acceso.ems.utils;

import es.ivan.acceso.ems.api.LocalizeName;
import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.api.Rank;

import java.util.*;

public class Table {

    public String medicsToTable(List<Medic> medics) {
        final List<String> headers = new ArrayList<>();
        final HashMap<Integer, List<String>> body = new HashMap<>();

        // --- Set headers ---
        Arrays.asList(medics.get(0).getClass().getDeclaredFields()).forEach(f -> headers.add(f.getAnnotation(LocalizeName.class).value()));
        // --- ---

        for (int i = 0; i < medics.size(); i++) {
            int finalI = i;
            final List<String> tempBody = new ArrayList<>();
            Arrays.asList(medics.get(i).getClass().getDeclaredFields()).forEach(f -> {
                f.setAccessible(true);
                try {
                    tempBody.add(String.valueOf(f.get(medics.get(finalI))));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            body.put(i, tempBody);
        }

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

        for (int i = 0; i < headers.size(); i++) {
            final int maxLength = Math.max(headers.get(i).length(), this.maxLength(body, i));
            headers.set(i, this.fixLength(headers.get(i), maxLength));

            int finalI = i;
            body.values().forEach(l -> this.fixLength(l.get(finalI), maxLength));
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(this.formatList(headers));
        sb.append("\n");
        body.forEach((id, list) -> sb.append(this.formatList(list)).append("\n"));

        return sb.toString();
    }

    /**
     *
     *
     * @param medic
     * @return
     */
    public String medicToTable(Medic medic) {
        final List<String> headers = new ArrayList<>();
        final List<String> body = new ArrayList<>();

        // --- Set headers ---
        Arrays.asList(medic.getClass().getDeclaredFields()).forEach(f -> {
            final String localizeName = f.getAnnotation(LocalizeName.class).value();
            if (localizeName.equalsIgnoreCase("usuario")) return;
            headers.add(localizeName);
        });
        // --- ---

        Arrays.asList(medic.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            try {
                body.add(String.valueOf(f.get(medic)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        body.removeIf(content -> content.equalsIgnoreCase("___"));

        for (int i = 0; i < body.size(); i++) {
            switch (i) {
                case 3:
                    body.set(i, medic.getRank().getDisplay());
                    break;
                case 4:
                case 5:
                    body.set(i, body.get(i).equalsIgnoreCase("1") ? "Sí" : "No");
                    break;
            }
        }

        for (int i = 0; i < headers.size(); i++) {
            final int maxLength = Math.max(headers.get(i).length(), body.get(i).length());
            headers.set(i, this.fixLength(headers.get(i), maxLength));
            body.set(i, this.fixLength(body.get(i), maxLength));
        }

        return this.formatList(headers) + "\n" + this.formatList(body);
    }

    // --- Utils ---
    private String formatList(final List<String> list) {
        return list.toString().replaceAll("\\[", "| ").replaceAll("]", " |").replaceAll(",", "|");
    }

    private String fixLength(String text, int length) {
        final StringBuilder textBuilder = new StringBuilder(text);
        for (int i = textBuilder.length(); i <= length; i++) textBuilder.append(" ");
        return textBuilder.toString();
    }

    private int maxLength(HashMap<Integer, List<String>> body, int search) {
        final Integer[] lengths = new Integer[body.keySet().size()];
        body.forEach((id, content) -> lengths[id] = content.get(search).length());
        Arrays.sort(lengths, (l1, l2) -> l1 > l2 ? 1 : -1);
        return lengths[0];
    }
}
