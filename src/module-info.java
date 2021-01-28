module Restaurant {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    opens Application;
    opens Application.Controller;
    opens Application.Model;
    opens Application.View;
    opens Application.Resources;
}