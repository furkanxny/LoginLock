module com.mycompany.password_managment_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.mycompany.password_managment_system to javafx.fxml;
    exports com.mycompany.password_managment_system;
}
