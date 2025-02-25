module ma.enset.finprojectjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.enset.finprojectjavafx to javafx.fxml;
    exports ma.enset.finprojectjavafx;
}