package es.ivan.acceso.ems.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Rank {

    JEFE(5, "Jefe Médico"),
    ENCARGADO(4, "Encargado"),
    MEDICO(3, "Médico"),
    ENFERMERO(2, "Enfermero"),
    AUXILIAR(1, "Auxiliar"),
    CELADOR(0, "Celador");


    @Getter private final int rank;
    @Getter private final String display;

    public static Rank parseRank(int rank) {
        return Arrays.stream(Rank.values()).filter(r -> r.getRank() == rank).findAny().orElse(JEFE);
    }
}
