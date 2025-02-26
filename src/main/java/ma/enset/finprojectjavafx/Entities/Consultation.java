package ma.enset.finprojectjavafx.Entities;

import java.sql.Date;

public class Consultation {
    private long id_consultation;
    private Date date;
    private String description;
    private Patient patient;

    public long getId_consultation() {
        return id_consultation;
    }

    public void setId_consultation(long id_consultation) {
        this.id_consultation = id_consultation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Consultation() {
    }

    public Consultation(Patient patient, String description, Date date) {
        this.patient = patient;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id_consultation=" + id_consultation +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", patient=" + patient +
                '}';
    }
}
