package ma.enset.finprojectjavafx.controllers;


import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;
import ma.enset.finprojectjavafx.dao.ConsultaionDao;
import ma.enset.finprojectjavafx.dao.PatientDao;
import ma.enset.finprojectjavafx.service.CabinetService;

import java.sql.Date;
import java.time.LocalDate;

public class ConsultationController {
    @FXML
    private TableView<Consultation> consultationTable;
    @FXML
    private TableColumn<Consultation, Integer> idCol;
    @FXML
    private TableColumn<Consultation, LocalDate> dateCol;
    @FXML
    private TableColumn<Consultation, String> descriptionCol;
    @FXML
    private TableColumn<Consultation, Patient> patientCol;
    @FXML
    private TextField idField, descriptionField, searchField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private Button newBtn, createBtn, updateBtn, deleteBtn;

    private DataModel dataModel;
    private FilteredList<Consultation> filteredList;
    private int nextId = 1;
    private CabinetService cabinetService = new CabinetService(new PatientDao(), new ConsultaionDao());

    public ConsultationController() {
        this.dataModel = new DataModel();
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize() {

        if (dataModel == null) {
            dataModel = new DataModel();
            dataModel.setPatients(FXCollections.observableArrayList());
        }

        for (Consultation consultation : cabinetService.getALlConsultations()) {
            Consultation c = new Consultation();
            c.setId_consultation(consultation.getId_consultation());
            c.setDate(consultation.getDate());
            c.setDescription(consultation.getDescription());
            c.setPatient(consultation.getPatient());
            System.out.println(c);
            dataModel.getConsultations().add(c);
        }


        idCol.setCellValueFactory(new PropertyValueFactory<>("id_consultation"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patient"));

        filteredList = new FilteredList<>(dataModel.getConsultations(), c -> true);
        consultationTable.setItems(filteredList);

        patientComboBox.setItems(dataModel.getPatients());

        consultationTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getId_consultation()));
                datePicker.setValue(newSel.getDate().toLocalDate());
                descriptionField.setText(newSel.getDescription());
                patientComboBox.setValue(newSel.getPatient());
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
                createBtn.setDisable(true);
            }
        });
    }

    @FXML
    private void search() {
        String searchText = searchField.getText().toLowerCase();
        filteredList.setPredicate(c ->
                c.getDescription().toLowerCase().contains(searchText) ||
                        c.getPatient().toString().toLowerCase().contains(searchText)
        );
    }

    @FXML
    private void newConsultation() {
        idField.setText(String.valueOf(nextId));
        datePicker.setValue(LocalDate.now());
        descriptionField.clear();
        patientComboBox.getSelectionModel().clearSelection();
        createBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        consultationTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void createConsultation() {
        Consultation cons = new Consultation();
//        cons.setId_consultation(nextId++);
        cons.setDate(Date.valueOf(datePicker.getValue()));
        cons.setDescription(descriptionField.getText());
        cons.setPatient(patientComboBox.getValue());
        cabinetService.addConsultation(cons);
        dataModel.getConsultations().add(cons);
        refreachData();
        newConsultation();
    }

    @FXML
    private void updateConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setDate(Date.valueOf(datePicker.getValue()));
            selected.setDescription(descriptionField.getText());
            selected.setPatient(patientComboBox.getValue());
            cabinetService.updateConsultation(selected);
            consultationTable.refresh();
        }
    }

    @FXML
    private void deleteConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cabinetService.deleteConsultation(selected);
            dataModel.getConsultations().remove(selected);
            newConsultation();
            clear();
        }
    }

    @FXML
    private void clear() {
//        idField.setText(String.valueOf(nextId));
        datePicker.setValue(LocalDate.now());
        descriptionField.clear();
        patientComboBox.getSelectionModel().clearSelection();
        createBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        consultationTable.getSelectionModel().clearSelection();
        refreachData();
        newConsultation();
    }

    void refreachData() {
        dataModel.getConsultations().clear();
        for (Consultation consultation : cabinetService.getALlConsultations()) {
            Consultation cons = new Consultation();
            cons.setId_consultation(consultation.getId_consultation());
            cons.setDescription(consultation.getDescription());
            cons.setPatient(consultation.getPatient());
            cons.setDate(consultation.getDate());
//            System.out.println(p);
            dataModel.getConsultations().add(cons);
        }
        filteredList = new FilteredList<>(dataModel.getConsultations(), p -> true);
        consultationTable.setItems(filteredList);
        consultationTable.refresh();
    }
}
