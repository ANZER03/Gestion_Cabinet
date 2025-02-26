package ma.enset.finprojectjavafx;

//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//
//public class HelloController {
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
//}


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HelloController extends Application {

    // Model class for Patient
    public static class Patient {
        private Integer id;
        private String nom;
        private String prenom;
        private String tel;

        public Patient(Integer id, String nom, String prenom, String tel) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.tel = tel;
        }

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getTel() { return tel; }
        public void setTel(String tel) { this.tel = tel; }

        @Override
        public String toString() {
            return nom + " " + prenom;
        }
    }

    // Form fields
    private TextField txtId = new TextField();
    private TextField txtNom = new TextField();
    private TextField txtPrenom = new TextField();
    private TextField txtTel = new TextField();
    private TextField txtSearch = new TextField();

    // Buttons
    private Button btnSave = new Button("Enregistrer");
    private Button btnUpdate = new Button("Modifier");
    private Button btnDelete = new Button("Supprimer");
    private Button btnClear = new Button("Nouveau");

    // Table
    private TableView<Patient> tablePatients = new TableView<>();
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
    private FilteredList<Patient> filteredData;

    // Status label
    private Label lblStatus = new Label("");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f5f5f7;");

        // Header
        HBox headerBox = createHeader();
        root.setTop(headerBox);

        // Form
        VBox formBox = createForm();

        // Button panel
        HBox buttonBox = createButtonPanel();

        // Combine form and buttons
        VBox leftPanel = new VBox(20);
        leftPanel.getChildren().addAll(formBox, buttonBox, lblStatus);
        leftPanel.setPadding(new Insets(10));
        root.setLeft(leftPanel);

        // Table and search
        VBox rightPanel = createTablePanel();
        root.setCenter(rightPanel);

        // Set up event handlers
        setupEventHandlers();

        // Add some sample data
        addSampleData();

        // Set up scene
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Gestion Cabinet - Patients");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createHeader() {
        Label headerLabel = new Label("Gestion des Patients");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: #3498db; -fx-background-radius: 5 5 0 0;");
        headerBox.getChildren().add(headerLabel);
        return headerBox;
    }

    private VBox createForm() {
        // Make ID field read-only (auto-generated)
        txtId.setEditable(false);
        txtId.setPromptText("Auto-généré");

        // Set prompt text for other fields
        txtNom.setPromptText("Entrez le nom du patient");
        txtPrenom.setPromptText("Entrez le prénom du patient");
        txtTel.setPromptText("Entrez le numéro de téléphone");

        // Create field containers with labels
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");

        // Add labels and fields to grid
        grid.add(createFieldLabel("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(createFieldLabel("Nom:"), 0, 1);
        grid.add(txtNom, 1, 1);
        grid.add(createFieldLabel("Prénom:"), 0, 2);
        grid.add(txtPrenom, 1, 2);
        grid.add(createFieldLabel("Téléphone:"), 0, 3);
        grid.add(txtTel, 1, 3);

        // Set column constraints for responsive layout
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        grid.getColumnConstraints().addAll(col1, col2);

        // Style the text fields
        styleTextFields();

        // Create titled form
        Label formTitle = new Label("Information du Patient");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        formTitle.setTextFill(Color.web("#2c3e50"));

        VBox formBox = new VBox(15);
        formBox.getChildren().addAll(formTitle, grid);

        return formBox;
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#34495e"));
        return label;
    }

    private void styleTextFields() {
        String textFieldStyle = "-fx-background-color: #f8f9fa; " +
                "-fx-border-color: #ced4da; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 8;";

        txtId.setStyle(textFieldStyle);
        txtNom.setStyle(textFieldStyle);
        txtPrenom.setStyle(textFieldStyle);
        txtTel.setStyle(textFieldStyle);
        txtSearch.setStyle(textFieldStyle + "-fx-background-radius: 20; -fx-border-radius: 20;");

        // Set preferred width for consistency
        txtId.setPrefWidth(250);
        txtNom.setPrefWidth(250);
        txtPrenom.setPrefWidth(250);
        txtTel.setPrefWidth(250);
    }

    private HBox createButtonPanel() {
        // Style buttons
        btnSave.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        btnUpdate.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        btnClear.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");

        // Add hover effect
        addButtonHoverEffect(btnSave, "#27ae60", "#2ecc71");
        addButtonHoverEffect(btnUpdate, "#2980b9", "#3498db");
        addButtonHoverEffect(btnDelete, "#c0392b", "#e74c3c");
        addButtonHoverEffect(btnClear, "#7f8c8d", "#95a5a6");

        // Set preferred width for buttons
        btnSave.setPrefWidth(110);
        btnUpdate.setPrefWidth(110);
        btnDelete.setPrefWidth(110);
        btnClear.setPrefWidth(110);

        // Add icons to buttons (you would need to include these in your project)
        // btnSave.setGraphic(new ImageView(new Image("/icons/save.png")));

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnSave, btnUpdate, btnDelete, btnClear);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        return buttonBox;
    }

    private void addButtonHoverEffect(Button button, String baseColor, String hoverColor) {
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle().replace(hoverColor, baseColor)));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(baseColor, hoverColor)));
    }

    private VBox createTablePanel() {
        // Search box
        HBox searchBox = new HBox(10);
        Label searchLabel = new Label("Rechercher:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchLabel.setTextFill(Color.web("#34495e"));

        txtSearch.setPrefWidth(300);
        txtSearch.setMaxWidth(Double.MAX_VALUE);
        txtSearch.setPromptText("Rechercher par nom ou prénom...");

        // Add search icon (you would need to include this in your project)
        // ImageView searchIcon = new ImageView(new Image("/icons/search.png"));
        // txtSearch.setLeft(searchIcon);

        searchBox.getChildren().addAll(searchLabel, txtSearch);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(0, 5, 10, 5));

        // Setup table
        setupTable();

        // Container for table and search
        VBox tableContainer = new VBox(10);
        tableContainer.setPadding(new Insets(10));
        tableContainer.getChildren().addAll(searchBox, tablePatients);

        return tableContainer;
    }

    private void setupTable() {
        // Create table columns
        TableColumn<Patient, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Patient, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Patient, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Patient, String> telCol = new TableColumn<>("Téléphone");
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));

        // Set column widths
        idCol.setPrefWidth(80);
        nomCol.setPrefWidth(150);
        prenomCol.setPrefWidth(150);
        telCol.setPrefWidth(150);

        // Add columns to table
        tablePatients.getColumns().addAll(idCol, nomCol, prenomCol, telCol);

        // Set row factory for styling
        tablePatients.setRowFactory(new Callback<TableView<Patient>, TableRow<Patient>>() {
            @Override
            public TableRow<Patient> call(TableView<Patient> tableView) {
                final TableRow<Patient> row = new TableRow<Patient>() {
                    @Override
                    protected void updateItem(Patient patient, boolean empty) {
                        super.updateItem(patient, empty);
                        if (empty) {
                            setStyle("");
                        } else {
                            // Alternate row colors
                            if (getIndex() % 2 == 0) {
                                setStyle("-fx-background-color: #f8f9fa;");
                            } else {
                                setStyle("-fx-background-color: #e9ecef;");
                            }

                            // Hover effect
                            setOnMouseEntered(e -> setStyle("-fx-background-color: #d1e7ff;"));
                            setOnMouseExited(e -> {
                                if (getIndex() % 2 == 0) {
                                    setStyle("-fx-background-color: #f8f9fa;");
                                } else {
                                    setStyle("-fx-background-color: #e9ecef;");
                                }
                            });
                        }
                    }
                };
                return row;
            }
        });

        // Set table style
        tablePatients.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");

        // Set placeholder when table is empty
        tablePatients.setPlaceholder(new Label("Aucun patient disponible"));

        // Setup filtered data
        filteredData = new FilteredList<>(patientData, p -> true);
        tablePatients.setItems(filteredData);
    }

    private void setupEventHandlers() {
        // Table selection event
        tablePatients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId().toString());
                txtNom.setText(newSelection.getNom());
                txtPrenom.setText(newSelection.getPrenom());
                txtTel.setText(newSelection.getTel());
            }
        });

        // Search functionality
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                // If filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (patient.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name
                } else if (patient.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name
                } else if (patient.getTel().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches phone
                }
                return false; // Does not match
            });
        });

        // Save button
        btnSave.setOnAction(e -> {
            if (validateInput()) {
                savePatient();
            }
        });

        // Update button
        btnUpdate.setOnAction(e -> {
            if (validateInput()) {
                updatePatient();
            }
        });

        // Delete button
        btnDelete.setOnAction(e -> deletePatient());

        // Clear button
        btnClear.setOnAction(e -> clearForm());
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (txtNom.getText().trim().isEmpty()) {
            errorMessage.append("Le nom du patient est requis.\n");
        }

        if (txtPrenom.getText().trim().isEmpty()) {
            errorMessage.append("Le prénom du patient est requis.\n");
        }

        if (txtTel.getText().trim().isEmpty()) {
            errorMessage.append("Le numéro de téléphone est requis.\n");
        }

        if (errorMessage.length() > 0) {
            lblStatus.setText(errorMessage.toString());
            lblStatus.setTextFill(Color.RED);
            return false;
        }

        return true;
    }

    private void savePatient() {
        try {
            // Generate new ID (in a real app, this would come from database)
            int newId = patientData.size() + 1;

            Patient newPatient = new Patient(
                    newId,
                    txtNom.getText().trim(),
                    txtPrenom.getText().trim(),
                    txtTel.getText().trim()
            );

            patientData.add(newPatient);
            clearForm();

            lblStatus.setText("Patient ajouté avec succès.");
            lblStatus.setTextFill(Color.GREEN);
        } catch (Exception e) {
            lblStatus.setText("Erreur: " + e.getMessage());
            lblStatus.setTextFill(Color.RED);
        }
    }

    private void updatePatient() {
        try {
            if (txtId.getText().isEmpty()) {
                lblStatus.setText("Veuillez sélectionner un patient à modifier.");
                lblStatus.setTextFill(Color.RED);
                return;
            }

            Integer id = Integer.parseInt(txtId.getText());

            // Find the patient with the matching ID
            for (Patient patient : patientData) {
                if (patient.getId().equals(id)) {
                    patient.setNom(txtNom.getText().trim());
                    patient.setPrenom(txtPrenom.getText().trim());
                    patient.setTel(txtTel.getText().trim());

                    // Refresh table
                    tablePatients.refresh();
                    clearForm();

                    lblStatus.setText("Patient modifié avec succès.");
                    lblStatus.setTextFill(Color.GREEN);
                    return;
                }
            }

            lblStatus.setText("Patient introuvable.");
            lblStatus.setTextFill(Color.RED);
        } catch (Exception e) {
            lblStatus.setText("Erreur: " + e.getMessage());
            lblStatus.setTextFill(Color.RED);
        }
    }

    private void deletePatient() {
        try {
            if (txtId.getText().isEmpty()) {
                lblStatus.setText("Veuillez sélectionner un patient à supprimer.");
                lblStatus.setTextFill(Color.RED);
                return;
            }

            Integer id = Integer.parseInt(txtId.getText());

            // Find and remove the patient with the matching ID
            Patient patientToRemove = null;
            for (Patient patient : patientData) {
                if (patient.getId().equals(id)) {
                    patientToRemove = patient;
                    break;
                }
            }

            if (patientToRemove != null) {
                patientData.remove(patientToRemove);
                clearForm();

                lblStatus.setText("Patient supprimé avec succès.");
                lblStatus.setTextFill(Color.GREEN);
            } else {
                lblStatus.setText("Patient introuvable.");
                lblStatus.setTextFill(Color.RED);
            }
        } catch (Exception e) {
            lblStatus.setText("Erreur: " + e.getMessage());
            lblStatus.setTextFill(Color.RED);
        }
    }

    private void clearForm() {
        txtId.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtTel.clear();
        tablePatients.getSelectionModel().clearSelection();
    }

    private void addSampleData() {
        patientData.addAll(
                new Patient(1, "Dupont", "Jean", "0612345678"),
                new Patient(2, "Martin", "Sophie", "0723456789"),
                new Patient(3, "Dubois", "Pierre", "0634567890"),
                new Patient(4, "Leroy", "Marie", "0745678901"),
                new Patient(5, "Moreau", "Philippe", "0656789012")
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}