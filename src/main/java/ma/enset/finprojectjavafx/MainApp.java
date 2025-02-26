package ma.enset.finprojectjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApp.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cabinet Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}