package com.example.projectloginpage.controllers;

import com.example.projectloginpage.exceptions.EmailAlreadyExistsException;
import com.example.projectloginpage.exceptions.PasswordMismatchException;
import com.example.projectloginpage.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable{

    @FXML private ChoiceBox<String> choiceBox;

    @FXML private TextField fullNameTextField, emailTextField;
    @FXML private PasswordField passwordTextField, confirmPasswordTextField;

    @FXML VBox emailErrorVbox;
    @FXML VBox passwordMismatchErrorVbox;

    private UserService userService = UserService.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiceBox.getItems().addAll("Student", "Teacher");
        choiceBox.setValue("Student");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Selected: " + newValue);
        });
    }

    @FXML
    public void linkToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectloginpage/FxmlFiles/Login.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    void linkToMainPage(ActionEvent event) throws IOException{

        //clear all the error labels in both VBoxes
        emailErrorVbox.getChildren().clear();
        passwordMismatchErrorVbox.getChildren().clear();

        String fullName = fullNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        String role = choiceBox.getValue();

        try {
            userService.signUp(fullName, email, password, confirmPassword, role);

            String filePath;
            if (role.equalsIgnoreCase("student"))
                filePath = "/com/example/projectloginpage/FxmlFiles/ForStudents/MainLayoutForStudents.fxml";
            else
                filePath = "/com/example/projectloginpage/FxmlFiles/ForTeachers/MainLayoutForTeachers.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            stage.setScene(scene);
            scene.setRoot(root);
            stage.setMaximized(true);
            stage.show();
        }
        catch(EmailAlreadyExistsException e){
            System.out.println(e.getMessage());
            showError(emailErrorVbox, e.getMessage());
        }
        catch(PasswordMismatchException e){
            showError(passwordMismatchErrorVbox, e.getMessage());
        }
    }

    private void showError(VBox vbox, String error){
        Label errorLabel = new Label(error);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        vbox.getChildren().add(errorLabel);
    }
}