package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Intervention;
import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class InterventionsQuery extends AbstractQuery {

    public List<Intervention> getAllInterventionsFromPatient(int patientId) {
        final List<Intervention> interventions = new ArrayList<>();

        try {
            final PreparedStatement interventionStatement = this.preparedStatement("select * from `checks` where `userID`=?");
            interventionStatement.setInt(1, patientId);
            final ResultSet resultSet = interventionStatement.executeQuery();

            while (resultSet.next()) {
                final Medic medic = new MedicQuery().getMedicById(resultSet.getInt("emsID"));
                final Patient patient = new PatientQuery().getPatientById(resultSet.getInt("userID"));
                interventions.add(new Intervention(resultSet.getInt("id"), medic, patient, resultSet.getString("problem"), Instant.ofEpochMilli(resultSet.getTimestamp("date").getTime())));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return interventions;
    }

    public boolean addIntervention(Intervention intervention) {
        try {
            final PreparedStatement interventionStatement = this.preparedStatement("insert into `checks`(`emsID`,`userID`,`problem`) values (?,?,?)");
            interventionStatement.setInt(1, intervention.getMedic().getId());
            interventionStatement.setInt(2, intervention.getPatient().getId());
            interventionStatement.setString(3, intervention.getProblem());
            interventionStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean removeIntervention(int intervention) {
        try {
            final PreparedStatement removeStatement = this.preparedStatement("delete from `checks` where `id`=?");
            removeStatement.setInt(1, intervention);
            removeStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }
}
