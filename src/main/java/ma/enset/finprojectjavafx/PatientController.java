package ma.enset.finprojectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.finprojectjavafx.Entities.Patient;

public class PatientController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Long> idCol;
    @FXML private TableColumn<Patient, String> nomCol, prenomCol, telCol;
    @FXML private TextField idField, nomField, prenomField, telField, searchField;
    @FXML private Button newBtn, createBtn, updateBtn, deleteBtn;

    private DataModel dataModel;
    private FilteredList<Patient> filteredList;
    private int nextId = 1;

    // Default no-arg constructor needed by FXMLLoader
    public PatientController() {
        // Initialize dataModel here or via a setter method
        this.dataModel = new DataModel();
        // If DataModel doesn't exist yet, you might need to create it
        // this.dataModel = new DataModel();
    }

    // Setter for dependency injection
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize() {
        // If dataModel isn't injected, create it here
        if (dataModel == null) {
            dataModel = new DataModel();
            dataModel.setPatients(FXCollections.observableArrayList());
        }

        // Fix the property name to match your model
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));

        filteredList = new FilteredList<>(dataModel.getPatients(), p -> true);
        patientTable.setItems(filteredList);

        // Initialize nextId based on existing patients
        if (!dataModel.getPatients().isEmpty()) {
            for (Patient p : dataModel.getPatients()) {
                if (p.getId_patient() >= nextId) {
                    nextId = (int) (p.getId_patient() + 1);
                }
            }
        }

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getId_patient()));
                nomField.setText(newSel.getNom());
                prenomField.setText(newSel.getPrenom());
                telField.setText(newSel.getTel());
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
                createBtn.setDisable(true);
            }
        });

        // Add listener for search field to dynamically update as user types
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });
    }

    @FXML
    private void search() {
        String searchText = searchField.getText().toLowerCase();
        filteredList.setPredicate(p ->
                searchText.isEmpty() || // Show all if search is empty
                        p.getNom().toLowerCase().contains(searchText) ||
                        p.getPrenom().toLowerCase().contains(searchText) ||
                        p.getTel().contains(searchText)
        );
    }

    @FXML
    private void newPatient() {
        idField.setText(String.valueOf(nextId));
        nomField.clear();
        prenomField.clear();
        telField.clear();
        createBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        patientTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void createPatient() {
        // Validate input fields
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty()) {
            showAlert("Input Error", "Name and first name cannot be empty");
            return;
        }

        Patient patient = new Patient();
        patient.setId_patient(nextId++);
        patient.setNom(nomField.getText());
        patient.setPrenom(prenomField.getText());
        patient.setTel(telField.getText());
        dataModel.getPatients().add(patient);
        newPatient();
    }

    @FXML
    private void updatePatient() {
        // Validate input fields
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty()) {
            showAlert("Input Error", "Name and first name cannot be empty");
            return;
        }

        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(nomField.getText());
            selected.setPrenom(prenomField.getText());
            selected.setTel(telField.getText());
            patientTable.refresh();
        }
    }

    @FXML
    private void deletePatient() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataModel.getPatients().remove(selected);
            newPatient();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}