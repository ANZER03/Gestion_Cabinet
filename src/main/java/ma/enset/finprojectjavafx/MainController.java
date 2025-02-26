package ma.enset.finprojectjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        // Load initial view, for example, Patients view
        loadPatientsView();
    }

    @FXML
    private void loadPatientsView() {
        try {
            // Load the Patients view FXML file
            Parent patientsView = FXMLLoader.load(MainController.class.getResource("view.fxml")); // Assuming patients-view.fxml is in the same package
            contentArea.getChildren().setAll(patientsView); // Set the loaded view as the content of contentArea
        } catch (IOException e) {
            System.out.println(e.getMessage()); // Handle exception properly in a real application
            // You might want to show an error message to the user instead of printing to console
        }
    }

    @FXML
    private void loadConsultationsView() {
        try {
            // Load the Consultations view FXML file
            Parent consultationsView = FXMLLoader.load(MainController.class.getResource("consultation.fxml")); // Assuming consultations-view.fxml is in the same package
            contentArea.getChildren().setAll(consultationsView); // Set the loaded view as the content of contentArea
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception properly in a real application
            // You might want to show an error message to the user instead of printing to console
        }
    }

    // Method to be called when the "Patients" button is clicked
    @FXML
    private void handlePatientsButtonClick() {
        loadPatientsView();
    }

    // Method to be called when the "Consultations" button is clicked
    @FXML
    private void handleConsultationsButtonClick() {
        loadConsultationsView();
    }

    public static void main(String[] args) {
        System.out.println("Resource path: " + MainController.class.getResource("consultation.fxml"));
    }
}