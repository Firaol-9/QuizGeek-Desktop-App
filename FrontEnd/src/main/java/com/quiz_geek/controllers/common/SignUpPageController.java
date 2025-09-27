package com.quiz_geek.controllers.common;

import com.quiz_geek.exceptions.EmailAlreadyExistsException;
import com.quiz_geek.exceptions.PasswordMismatchException;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.payloads.UserDTO;
import com.quiz_geek.services.core.ApiService;
import com.quiz_geek.services.core.UserService;
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

    @FXML private ChoiceBox<UserRole> choiceBox;

    @FXML private TextField fullNameTextField, emailTextField;
    @FXML private PasswordField passwordTextField, confirmPasswordTextField;

    @FXML VBox emailErrorVbox;
    @FXML VBox passwordMismatchErrorVbox;

    private final UserService userService = UserService.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiceBox.getItems().addAll(UserRole.values());
        choiceBox.setValue(UserRole.STUDENT);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
        });
    }

    @FXML
    public void linkToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/common/Login.fxml"));
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
        UserRole role = choiceBox.getValue();

        try {
            UserDTO userDTO = ApiService.signup(fullName, email, password, role);
            String filePath;
            if (role == UserRole.STUDENT)
                filePath = "ForStudents/MainLayoutForStudents.fxml";
            else
                filePath = "ForTeachers/MainLayoutForTeachers.fxml";

            Parent root = SceneManager.getPage(filePath);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            stage.setScene(scene);
            scene.setRoot(root);
            stage.setMaximized(true);
            stage.show();
        }
        catch(Exception e){
            showError(emailErrorVbox, e.getMessage());
        }
    }

    private void showError(VBox vbox, String error){
        Label errorLabel = new Label(error);
        errorLabel.getStyleClass().addAll("label-errorRed", "label-verySmall");
        vbox.getChildren().add(errorLabel);
    }
}