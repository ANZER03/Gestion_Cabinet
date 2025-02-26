package ma.enset.finprojectjavafx;


import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.finprojectjavafx.Entities.Consultation;
import ma.enset.finprojectjavafx.Entities.Patient;

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

    public ConsultationController(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        cons.setId_consultation(nextId++);
        cons.setDate(Date.valueOf(datePicker.getValue()));
        cons.setDescription(descriptionField.getText());
        cons.setPatient(patientComboBox.getValue());
        dataModel.getConsultations().add(cons);
        newConsultation();
    }

    @FXML
    private void updateConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setDate(Date.valueOf(datePicker.getValue()));
            selected.setDescription(descriptionField.getText());
            selected.setPatient(patientComboBox.getValue());
            consultationTable.refresh();
        }
    }

    @FXML
    private void deleteConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataModel.getConsultations().remove(selected);
            newConsultation();
        }
    }
}
