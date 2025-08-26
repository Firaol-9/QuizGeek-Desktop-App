package com.example.projectloginpage.controllers;

import com.example.projectloginpage.exceptions.EmailNotFoundException;
import com.example.projectloginpage.exceptions.IncorrectPasswordException;
import com.example.projectloginpage.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML VBox emailErrorVbox;
    @FXML VBox incorrectPasswordErrorVbox;

    private UserService userService = UserService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService.addUser();
    }

    @FXML
    void linkToSignUpPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectloginpage/FxmlFiles/Signup.fxml"));
        Parent root = loader.load();

        Stage stage =  (Stage)( (Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        stage.setScene(scene);
        scene.setRoot(root);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    void linkToMainPage(ActionEvent event) throws IOException{

        //clear the error labels in the Vboxes if there are any
        emailErrorVbox.getChildren().clear();
        incorrectPasswordErrorVbox.getChildren().clear();

        String email = emailTextField.getText();
        String password = passwordTextField.getText();


        try {
            userService.login(email, password);

            String fxmlPath;
            if (UserService.getCurrentUser().getRole().equalsIgnoreCase("student"))
                fxmlPath = "/com/example/projectloginpage/FxmlFiles/ForStudents/MainLayoutForStudents.fxml";
            else
                fxmlPath = "/com/example/projectloginpage/FxmlFiles/ForTeachers/MainLayoutForTeachers.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            stage.setScene(scene);
            scene.setRoot(root);
            stage.setMaximized(true);
            stage.show();
        }
        catch(EmailNotFoundException e){
            showError(emailErrorVbox, e.getMessage());
        }
        catch(IncorrectPasswordException e){
            showError(incorrectPasswordErrorVbox, e.getMessage());
        }
    }
    private void showError(VBox vbox, String error){
        Label errorLabel = new Label(error);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        vbox.getChildren().add(errorLabel);
    }
}