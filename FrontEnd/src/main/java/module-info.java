module com.example.projectloginpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.kitfox.svg;
    requires javafx.swing;
    requires java.net.http;
    requires static lombok;
    requires com.fasterxml.jackson.databind;


    opens com.quiz_geek to javafx.fxml;
    exports com.quiz_geek;

    opens com.quiz_geek.payloads to com.fasterxml.jackson.databind;

    exports com.quiz_geek.models;
    opens com.quiz_geek.models to javafx.fxml;
    exports com.quiz_geek.controllers.student;
    opens com.quiz_geek.controllers.student to javafx.fxml;
    exports com.quiz_geek.controllers.teacher;
    opens com.quiz_geek.controllers.teacher to javafx.fxml;
    exports com.quiz_geek.controllers.common;
    opens com.quiz_geek.controllers.common to javafx.fxml;
    exports com.quiz_geek.services.core;
    opens com.quiz_geek.services.core to javafx.fxml;
    exports com.quiz_geek.exceptions;
    opens com.quiz_geek.exceptions to javafx.fxml;
}