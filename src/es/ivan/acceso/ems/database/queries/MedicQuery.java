package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicQuery extends AbstractQuery {

    public List<Medic> getAllMedics() {
        final List<Medic> medics = new ArrayList<>();
        try {
            final PreparedStatement medicStatement = this.preparedStatement("select `id`, `username`, `name`, `role`, `admin`, `active` from `users`");

            final ResultSet result = medicStatement.executeQuery();

            while (result.next()) {
                medics.add(new Medic(result.getInt("id"), result.getString("username"), result.getString("name"), result.getInt("role"), result.getInt("admin"), result.getInt("active")));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return medics;
    }
}
