package es.ivan.acceso.ems.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Medic extends AbstractAPI {

    private final int id;
    private final String name;

    private final int rank;
    private final int admin;

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
