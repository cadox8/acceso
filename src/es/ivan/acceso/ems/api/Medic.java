package es.ivan.acceso.ems.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medic extends AbstractAPI {

    @LocalizeName(value = "Id")
    private int id;

    @LocalizeName(value = "Usuario")
    private String username;

    @LocalizeName(value = "Nombre")
    private String name;

    @LocalizeName(value = "Rango")
    private int rank;
    @LocalizeName(value = "Administrador")
    private int admin;

    @LocalizeName(value = "¿Activo?")
    private int active;

    public Rank getRank() {
        return Rank.parseRank(this.rank);
    }
}
