package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.log.Log;

import java.sql.Date;
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

    public Medic getMedicById(int id) {
        Medic medic = null;
        try {
            final PreparedStatement medicStatement = this.preparedStatement("select `id`, `username`, `name`, `role`, `admin`, `active` from `users` where `id`=?");
            medicStatement.setInt(1, id);
            final ResultSet result = medicStatement.executeQuery();

            while (result.next()) {
                medic = new Medic(result.getInt("id"), result.getString("username"), result.getString("name"), result.getInt("role"),
                        result.getInt("admin"), result.getInt("active"));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return medic;
    }

    public boolean removeMedic(int medicId) {
        try {
            final PreparedStatement removeStatement = this.preparedStatement("delete from `users` where `id`=?");
            removeStatement.setInt(1, medicId);
            removeStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean addMedic(Medic medic) {
        try {
            final PreparedStatement patientsStatement = this.preparedStatement("insert into `users`(`name`, `username`, `role`, `admin`, `active`) values (?, ?, ?, ?, ?)");
            patientsStatement.setString(1, medic.getName());
            patientsStatement.setString(2, medic.getUsername());
            patientsStatement.setInt(3, medic.getRank().getRank());
            patientsStatement.setInt(4, medic.getAdmin());
            patientsStatement.setInt(5, medic.getActive());
            patientsStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }
}
