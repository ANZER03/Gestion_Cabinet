package ma.enset.finprojectjavafx.dao;

import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaionDao implements IConsultationDao {
    private final IPatientDao patientDao = new PatientDao();
    @Override
    public void create(Consultation consultation) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO consultation(date, description, id_patient) VALUES (?,?,?)");
        preparedStatement.setDate(1, consultation.getDate());
        preparedStatement.setString(2, consultation.getDescription());
        preparedStatement.setLong(3, consultation.getPatient().getId_patient());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Consultation consultation) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM consultation WHERE id_consultation = ?");
        preparedStatement.setLong(1, consultation.getId_consultation());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Consultation consultation) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `consultation` SET `date`=?,`description`=?,`id_patient`=? WHERE id_consultation = ?");
        preparedStatement.setDate(1, consultation.getDate());
        preparedStatement.setString(2, consultation.getDescription());
        preparedStatement.setLong(3, consultation.getPatient().getId_patient());
        preparedStatement.setLong(4 , consultation.getId_consultation());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Consultation> findall() throws SQLException {
        List<Consultation> consultations = new ArrayList<>();
        Connection connection = ConnectionDB.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM consultation");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Consultation consultation = new Consultation();
            consultation.setDate(resultSet.getDate("date"));
            consultation.setDescription(resultSet.getString("description"));
            consultation.setId_consultation(resultSet.getLong("id_consultation"));

            consultation.setPatient(patientDao.findById(resultSet.getLong("id_patient")));
            consultations.add(consultation);
        }


        return consultations;
    }

    @Override
    public Consultation findById(Long id) throws SQLException {
        Connection connection = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM consultation WHERE id_consultation = ?");
        preparedStatement.setLong(1 , id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Consultation consultation = new Consultation();

        if (resultSet.next()) {
            consultation.setId_consultation(resultSet.getLong("id_consultation"));
            consultation.setDate(resultSet.getDate("date"));
            consultation.setDescription(resultSet.getString("description"));
            consultation.setPatient(patientDao.findById(resultSet.getLong("id_patient")));
            return consultation;

        }


        return null;
    }

    public ConsultaionDao() {
    }

    public static void main(String[] args) throws SQLException {
        ConsultaionDao consultaionDao = new ConsultaionDao();
        PatientDao patientDao = new PatientDao();

        // First, get a patient by ID
        Patient patient = patientDao.findById(5L);

        // Create a new consultation with data
        Consultation consultation = new Consultation(
                patient,
                "Patient has fever and headache",
                new java.sql.Date(System.currentTimeMillis())
        );

        // Save the consultation
        consultaionDao.create(consultation);

        // Print all consultations to verify
        consultaionDao.findall().forEach(System.out::println);
    }


}
