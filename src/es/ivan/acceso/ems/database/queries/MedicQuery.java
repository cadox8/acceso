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

    public boolean addMedic(Medic medic, String password) {
        try {
            final PreparedStatement medicStatement = this.preparedStatement("insert into `users`(`name`, `username`, `role`, `admin`, `active`, `password`) values (?, ?, ?, ?, ?, ?)");
            medicStatement.setString(1, medic.getName());
            medicStatement.setString(2, medic.getUsername());
            medicStatement.setInt(3, medic.getRank().getRank());
            medicStatement.setInt(4, medic.getAdmin());
            medicStatement.setInt(5, medic.getActive());
            medicStatement.setString(6, password);
            medicStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean updateMedic(Medic medic) {
        try {
            final PreparedStatement medicStatement = this.preparedStatement("update `users` set `name`=?, `username`=?, `role`=?, `admin`=?, `active`=? where `id`=?");
            medicStatement.setString(1, medic.getName());
            medicStatement.setString(2, medic.getUsername());
            medicStatement.setInt(3, medic.getRank().getRank());
            medicStatement.setInt(4, medic.getAdmin());
            medicStatement.setInt(5, medic.getActive());
            medicStatement.setInt(6, medic.getId());
            medicStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean updatePassword(Medic medic, String newPassword) {
        try {
            final PreparedStatement medicStatement = this.preparedStatement("update `users` set `password`=? where `id`=?");
            medicStatement.setString(1, newPassword);
            medicStatement.setInt(2, medic.getId());
            medicStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }
}
