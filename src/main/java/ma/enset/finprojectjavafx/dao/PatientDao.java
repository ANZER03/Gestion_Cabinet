package ma.enset.finprojectjavafx.dao;

import ma.enset.finprojectjavafx.Entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements IPatientDao {
    @Override
    public void create(Patient patient) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patients (nom, prenom, tel) VALUES (?, ?, ?)");
        preparedStatement.setString(1, patient.getNom());
        preparedStatement.setString(2, patient.getPrenom());
        preparedStatement.setString(3, patient.getTel());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Patient patient) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM patients WHERE id = ?");
        preparedStatement.setLong(1, patient.getId_patient());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Patient patient) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE patients SET nom = ?, prenom = ?, tel = ? WHERE id = ?");
        preparedStatement.setString(1, patient.getNom());
        preparedStatement.setString(2, patient.getPrenom());
        preparedStatement.setString(3, patient.getTel());
        preparedStatement.setLong(4, patient.getId_patient());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Patient> findall() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        Connection connection = ConnectionDB.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patients");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setId_patient(resultSet.getLong("id"));
            patient.setNom(resultSet.getString("nom"));
            patient.setPrenom(resultSet.getString("prenom"));
            patient.setTel(resultSet.getString("tel"));
            patientList.add(patient);
        }


        return patientList;
    }

   @Override
   public Patient findById(Long id) throws SQLException {
       Connection connection = ConnectionDB.getConnection();
       Patient patient = new Patient();
       PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patients WHERE id = ?");
       preparedStatement.setLong(1, id);
       ResultSet resultSet = preparedStatement.executeQuery();
       if (resultSet.next()) {
           patient.setId_patient(resultSet.getLong("id"));
           patient.setNom(resultSet.getString("nom"));
           patient.setPrenom(resultSet.getString("prenom"));
           patient.setTel(resultSet.getString("tel"));
           return patient;
       }
       return null;
   }

    public PatientDao() {
    }

    public static void main(String[] args) {
        PatientDao patientDao = new PatientDao();

        try {

            Patient patient = patientDao.findById(patientDao.findall().get(0).getId_patient());
            patient.setPrenom("popi");
            patientDao.update(patient);

            List<Patient> patients = patientDao.findall();
            patients.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
