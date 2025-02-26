package ma.enset.finprojectjavafx.service;

import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;

import java.util.List;

public interface ICabinetService {

    void addPatient(Patient patient);
    void deletePatient(Patient patient);
    void updatePatient(Patient patient);
    List<Patient> getALlPatients();
    Patient getPatientById(Long id);




    void addConsultation(Consultation consultation);
    void deleteConsultation(Consultation consultation);
    void updateConsultation(Consultation consultation);
    List<Consultation> getALlConsultations();
    Consultation getConsultationtById(Long id);
}
