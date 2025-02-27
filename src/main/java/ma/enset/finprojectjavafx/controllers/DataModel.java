package ma.enset.finprojectjavafx.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;
import ma.enset.finprojectjavafx.dao.ConsultaionDao;
import ma.enset.finprojectjavafx.dao.PatientDao;
import ma.enset.finprojectjavafx.service.CabinetService;
import ma.enset.finprojectjavafx.service.ICabinetService;

public class DataModel {
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();
    private ICabinetService cabinetService = new CabinetService(new PatientDao(), new ConsultaionDao());
    public ObservableList<Patient> getPatients() {

        this.patients.clear();
        for (Patient p : cabinetService.getALlPatients()) {
            Patient patient = new Patient();
            patient.setId_patient(p.getId_patient());
            patient.setNom(p.getNom());
            patient.setPrenom(p.getPrenom());
            patient.setTel(p.getTel());
            System.out.println(p);
            this.patients.add(patient);
        }
        return patients;
    }

    public ObservableList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }
}