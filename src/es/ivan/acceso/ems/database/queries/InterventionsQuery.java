package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Interventions;
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

    public List<Interventions> getAllInterventionsFromPatient(int patientId) {
        final List<Interventions> interventions = new ArrayList<>();

        try {
            final PreparedStatement patientsStatement = this.preparedStatement("select * from `checks` where `userID`=?");
            patientsStatement.setInt(1, patientId);
            final ResultSet resultSet = patientsStatement.executeQuery();

            while (resultSet.next()) {
                final Medic medic = new MedicQuery().getMedicById(resultSet.getInt("emsID"));
                final Patient patient = new PatientQuery().getPatientById(resultSet.getInt("userID"));

                interventions.add(new Interventions(medic, patient, resultSet.getString("problem"), Instant.ofEpochMilli(resultSet.getTimestamp("date").getTime())));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }

        return interventions;
    }
}
