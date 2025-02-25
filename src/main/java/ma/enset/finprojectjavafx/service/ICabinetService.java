package ma.enset.finprojectjavafx.service;

import ma.enset.finprojectjavafx.Entities.Patient;

import java.util.List;

public interface ICabinetService {

    void addPatient(Patient patient);
    void deletePatient(Patient patient);
    void updatePatient(Patient patient);
    List<Patient> getALlPatients();
    Patient getPatientById(Long id);


}
