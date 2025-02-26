package ma.enset.finprojectjavafx.Entities;

import java.util.List;

public class Patient {
    private long id_patient;
    private String nom;
    private String prenom;
    private String tel;
    private List<Consultation> consultations;

    public Patient() {
    }

    public Patient(String nom, String prenom, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public String getPrenom() {
        return prenom;
    }

    public long getId_patient() {
        return id_patient;
    }

    public String getNom() {
        return nom;
    }

    public void setId_patient(long id_patient) {
        this.id_patient = id_patient;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id_patient=" + id_patient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
