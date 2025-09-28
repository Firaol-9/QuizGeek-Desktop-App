package com.quiz_geek.controllers.common;

import com.quiz_geek.exceptions.IncorrectPasswordOrEmailException;
import com.quiz_geek.exceptions.InvalidInputException;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.payloads.UserDTO;
import com.quiz_geek.services.core.ApiService;
import com.quiz_geek.services.core.UserService;

import com.quiz_geek.utils.UIHelpers;
import javafx.concurrent.Task;
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
    @FXML TextField emailTextField;
    @FXML PasswordField passwordTextField;
    @FXML VBox emailErrorVbox;
    @FXML VBox incorrectPasswordErrorVbox;

    private final UserService userService = UserService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIHelpers.nodeVisibility(emailErrorVbox, false);
        UIHelpers.nodeVisibility(incorrectPasswordErrorVbox, false);
    }

    @FXML
    void linkToSignUpPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/common/Signup.fxml"));
        Parent root = loader.load();

        Stage stage =  (Stage)( (Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        stage.setScene(scene);
        scene.setRoot(root);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    void linkToMainPage(ActionEvent event) {
        UIHelpers.nodeVisibility(emailErrorVbox, false);
        UIHelpers.nodeVisibility(incorrectPasswordErrorVbox, false);

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Define background task
        Task<UserDTO> task = new Task<>() {
            @Override
            protected UserDTO call() throws Exception {
                userService.validateLogin(email, password);
                return ApiService.login(email, password);
            }
        };

        // Success handler (runs on UI thread automatically)
        task.setOnSucceeded(e -> {
            UserDTO userDTO = task.getValue();
            String fxmlPath = "";

            if (userDTO.getRole() == UserRole.STUDENT)
                fxmlPath = "ForStudents/MainLayoutForStudents.fxml";
            else if (userDTO.getRole() == UserRole.TEACHER)
                fxmlPath = "ForTeachers/MainLayoutForTeachers.fxml";

            try {
                Parent root = SceneManager.getPage(fxmlPath);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = stage.getScene();
                stage.setScene(scene);
                scene.setRoot(root);
                stage.setMaximized(true);
                stage.show();
            } catch (IOException ex) {
                showError(emailErrorVbox, "Failed to load main page.");
            }
        });

        // Error handler (runs on UI thread automatically)
        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            if (ex instanceof InvalidInputException) {
                showError(emailErrorVbox, ex.getMessage());
            } else if (ex instanceof IncorrectPasswordOrEmailException) {
                showError(incorrectPasswordErrorVbox, ex.getMessage());
            } else {
                showError(emailErrorVbox, "Something went wrong: " + ex.getMessage());
            }
        });

        // Run task in background thread
        new Thread(task).start();
    }


    private void showError(VBox vbox, String error){
        vbox.getChildren().clear();
        vbox.getChildren().clear();
        UIHelpers.nodeVisibility(vbox, true);
        Label errorLabel = new Label(error);
        errorLabel.getStyleClass().addAll("label-errorRed", "label-verySmall");
        vbox.getChildren().add(errorLabel);
    }
}