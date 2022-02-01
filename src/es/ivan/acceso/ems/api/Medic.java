package es.ivan.acceso.ems.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Medic extends AbstractAPI {

    @LocalizeName(value = "Id")
    private final int id;

    @LocalizeName(value = "Usuario")
    private final String username;

    @LocalizeName(value = "Nombre")
    private final String name;

    @LocalizeName(value = "Rango")
    private final int rank;
    @LocalizeName(value = "Administrador")
    private final int admin;

    @LocalizeName(value = "¿Activo?")
    private final int active;


    public Rank getRank() {
        return Rank.parseRank(this.rank);
    }

    public boolean isAdmin() {
        return this.admin == 1;
    }

    public boolean isActive() {
        return this.active == 1;
    }
}
