module com.school.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.school.app to javafx.fxml;
    opens com.school.app.controller to javafx.fxml;
    opens com.school.app.model to javafx.base;

    exports com.school.app;
    exports com.school.app.controller;
    exports com.school.app.model;
}
