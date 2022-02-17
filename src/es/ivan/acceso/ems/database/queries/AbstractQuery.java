package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.database.Database;
import es.ivan.acceso.log.Log;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractQuery {

    @Getter
    private final Ems instance;
    @Getter
    private final Database database;

    public AbstractQuery() {
        this.instance = Ems.getInstance();
        this.database = this.instance.getDatabase();
    }

    public PreparedStatement preparedStatement(String query) {
        try {
            return this.database.openConnection().prepareStatement(query);
        } catch (SQLException | ClassNotFoundException e) {
            Log.stack(e.getStackTrace());
            Log.error("Ha ocurrido un error al realizar la consulta");
        }
        return null;
    }
}
