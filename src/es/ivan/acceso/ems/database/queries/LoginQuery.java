package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginQuery extends AbstractQuery {

    public Medic login(String username, String password) {
        Medic medic = null;
        try {
            final PreparedStatement loginStatement = this.preparedStatement("select `id`, `name`, `role`, `admin`, `active` from `users` where `username`=? and `password`=?");
            loginStatement.setString(1, username);
            loginStatement.setString(2, password);

            final ResultSet result = loginStatement.executeQuery();

            while (result.next()) {
                medic = new Medic(result.getInt("id"), "___", result.getString("name"), result.getInt("role"), result.getInt("admin"), result.getInt("active"));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return medic;
    }
}
