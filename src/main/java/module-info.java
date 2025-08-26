module com.example.projectloginpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.projectloginpage to javafx.fxml;
    exports com.example.projectloginpage;
    exports com.example.projectloginpage.controllers;
    opens com.example.projectloginpage.controllers to javafx.fxml;
    exports com.example.projectloginpage.services;
    opens com.example.projectloginpage.services to javafx.fxml;
    exports com.example.projectloginpage.models;
    opens com.example.projectloginpage.models to javafx.fxml;
}