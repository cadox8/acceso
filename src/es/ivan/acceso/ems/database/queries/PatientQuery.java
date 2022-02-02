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

    public Patient getPatientById(int id) {
        Patient patient = null;
        try {
            final PreparedStatement patientStatement = this.preparedStatement("select * from `users` where `id`=?");
            patientStatement.setInt(1, id);
            final ResultSet resultSet = patientStatement.executeQuery();

            while (resultSet.next()) {
                patient = new Patient(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDate("dob"),
                        resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getString("blood"));
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return patient;
    }

    public boolean removePatient(int patientId) {
        try {
            final PreparedStatement removeStatement = this.preparedStatement("delete from `patients` where `id`=?");
            removeStatement.setInt(1, patientId);
            removeStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean addPatient(Patient patient) {
        try {
            final PreparedStatement patientsStatement = this.preparedStatement("insert into `patients`(`name`, `phone`, `dob`, `height`, `weight`, `blood`) values (?, ?, ?, ?, ?, ?)");
            patientsStatement.setString(1, patient.getName());
            patientsStatement.setString(2, patient.getPhone());
            patientsStatement.setDate(3, new Date(patient.getDob().getTime()));
            patientsStatement.setDouble(4, patient.getHeight());
            patientsStatement.setDouble(5, patient.getWeight());
            patientsStatement.setString(6, patient.getBlood());
            patientsStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }

    public boolean editPatient(Patient patient) {
        try {
            final PreparedStatement patientsStatement = this.preparedStatement("insert into `patients`(`name`, `phone`, `dob`, `height`, `weight`, `blood`) values (?, ?, ?, ?, ?, ?)");
            patientsStatement.setString(1, patient.getName());
            patientsStatement.setString(2, patient.getPhone());
            patientsStatement.setDate(3, new Date(patient.getDob().getTime()));
            patientsStatement.setDouble(4, patient.getHeight());
            patientsStatement.setDouble(5, patient.getWeight());
            patientsStatement.setString(6, patient.getBlood());
            patientsStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            Log.stack(e.getStackTrace());
        }
        return false;
    }
}
