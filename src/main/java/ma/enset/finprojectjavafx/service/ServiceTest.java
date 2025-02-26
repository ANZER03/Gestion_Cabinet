package ma.enset.finprojectjavafx.service;

import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;
import ma.enset.finprojectjavafx.dao.ConsultaionDao;
import ma.enset.finprojectjavafx.dao.PatientDao;

import java.sql.Date;
import java.util.List;

public class ServiceTest {

    public static void main(String[] args) {

        ICabinetService cabinetService = new cabinetService(new PatientDao() , new ConsultaionDao());

//        Patient patient = cabinetService.getPatientById(cabinetService.getALlPatients().get(1).getId_patient());

//        cabinetService.updatePatient(patient);
//        Consultation consultation = new Consultation(patient , "Is have head damage??" , Date.valueOf("2000-09-18"));
        Consultation consultation = cabinetService.getConsultationtById(15L);
        cabinetService.deleteConsultation(consultation);



        List<Consultation> list = cabinetService.getALlConsultations();
        list.forEach(System.out::println);



    }
}
