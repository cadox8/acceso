package es.ivan.acceso.ems.database.queries;

import es.ivan.acceso.ems.api.Patient;
import es.ivan.acceso.log.Log;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientQuery extends AbstractQuery {

    public List<Patient> getPatients() {
        final List<Patient> patients = new ArrayList<>();

        try {
            final PreparedStatement patientsStatement = this.preparedStatement("select * from `patients`");
            final ResultSet resultSet = patientsStatement.executeQuery();

            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDate("dob"),
                        resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getString("blood")));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return patients;
    }

    public boolean addPatient(Patient patient) {
        try {
            final PreparedStatement patientsStatement = this.preparedStatement("insert into `patients`(`name`, `phone`, `dob`, `height`, `weight`, `blood`) values (?, ?, ?, ?, ?, ?)");
            patientsStatement.setString(0, patient.getName());
            patientsStatement.setString(1, patient.getPhone());
            patientsStatement.setDate(2, new Date(patient.getDob().getTime()));
            patientsStatement.setDouble(3, patient.getHeight());
            patientsStatement.setDouble(4, patient.getWeight());
            patientsStatement.setString(5, patient.getBlood());
            final ResultSet resultSet = patientsStatement.executeQuery();

            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }
}
