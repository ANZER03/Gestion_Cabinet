package ma.enset.finprojectjavafx.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;

public class DataModel {
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();

    public ObservableList<Patient> getPatients() {
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