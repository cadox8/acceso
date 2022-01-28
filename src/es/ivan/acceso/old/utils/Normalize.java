package es.ivan.acceso.old.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Normalize {

    A(new String[]{"á", "ä", "à", "ã", "â"}, "a"),
    E(new String[]{"é", "ë", "è", "ê"}, "e"),
    I(new String[]{"í", "ï", "ì", "î"}, "i"),
    O(new String[]{"ó", "ö", "ò", "õ", "ô"}, "o"),
    U(new String[]{"ú", "ü", "ù", "û"}, "u");

    private final String[] search;
    private final String replace;

    public static String normalizeWord(String word) {
        final StringBuilder sb = new StringBuilder();
        
        Arrays.asList(word.split("")).forEach(c -> {
            boolean replaced = false;
            boolean isUpper = Character.isUpperCase(c.charAt(0));
            c = c.toLowerCase();
            for (Normalize normalize : Normalize.values()) {
                for (String search : normalize.search) {
                    if (String.valueOf(search).equalsIgnoreCase(c)) {
                        c = c.replaceAll(c, normalize.replace);
                        replaced = true;
                        break;
                    }
                }
                if (replaced) break;
            }
            sb.append(isUpper ? c.toUpperCase() : c);
        });
        return sb.toString();
    }
}