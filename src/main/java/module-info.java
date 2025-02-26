module ma.enset.finprojectjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

//    opens ma.enset.finprojectjavafx.controllers to javafx.fxml;
    opens ma.enset.finprojectjavafx to javafx.fxml;
    opens ma.enset.finprojectjavafx.Entities to javafx.base;
//    exports ma.enset.finprojectjavafx.controllers to javafx.fxml;
    exports ma.enset.finprojectjavafx to javafx.fxml, javafx.graphics;
    exports ma.enset.finprojectjavafx.controllers to javafx.fxml, javafx.graphics;
    opens ma.enset.finprojectjavafx.controllers to javafx.fxml;
}