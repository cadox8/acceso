package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientQuery extends AbstractQuery {

    public List<Patient> getPatients(int limit) {
        final List<Patient> patients = new ArrayList<>();

        try {
            final PreparedStatement patientsStatement = this.preparedStatement("select * from `patients`");
            final ResultSet resultSet = patientsStatement.executeQuery();
            int rowCount = 0;

            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDate("dob"),
                        resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getString("blood")));
                rowCount++;
            }

            Log.info("[*] Pacientes totales: " + rowCount);
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return patients;
    }
}
