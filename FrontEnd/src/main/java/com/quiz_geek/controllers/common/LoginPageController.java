package com.quiz_geek.controllers.common;

import com.quiz_geek.exceptions.IncorrectPasswordOrEmailException;
import com.quiz_geek.exceptions.InvalidInputException;
import com.quiz_geek.mappers.UserMapper;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.payloads.UserDTO;
import com.quiz_geek.services.core.ApiService;
import com.quiz_geek.services.core.UserService;
import com.quiz_geek.utils.GoogleAuthHelper;
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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class LoginPageController implements Initializable {
    @FXML TextField emailTextField;
    @FXML PasswordField passwordTextField;
    @FXML VBox emailErrorVbox;
    @FXML VBox incorrectPasswordErrorVbox;
    @FXML VBox errorBox;

    private final UserService userService = UserService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIHelpers.nodeVisibility(emailErrorVbox, false);
        UIHelpers.nodeVisibility(incorrectPasswordErrorVbox, false);
        UIHelpers.nodeVisibility(errorBox, false);
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
        UIHelpers.nodeVisibility(errorBox, false);

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        Task<UserDTO> task = new Task<>() {
            @Override
            protected UserDTO call() throws Exception {
                userService.validateLogin(email, password);
                return ApiService.login(email, password);
            }
        };

        task.setOnSucceeded(e -> {
            UserDTO userDTO = task.getValue();
            UserService.getInstance().setCurrentUser(UserMapper.toUser(userDTO));
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
                UIHelpers.nodeVisibility(errorBox, true);
                showError(errorBox, "Failed to load main page.");
                ex.printStackTrace();
            }
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            if (ex instanceof InvalidInputException) {
                UIHelpers.nodeVisibility(errorBox, true);
                showError(errorBox, ex.getMessage());
            } else if (ex instanceof IncorrectPasswordOrEmailException) {
                showError(incorrectPasswordErrorVbox, ex.getMessage());
            } else {
                UIHelpers.nodeVisibility(errorBox, true);
                showError(errorBox, "Something went wrong: " + ex.getMessage());
            }
        });

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

    @FXML
    public void loginWithGoogle(ActionEvent event) {
        UIHelpers.nodeVisibility(emailErrorVbox, false);
        UIHelpers.nodeVisibility(incorrectPasswordErrorVbox, false);
        UIHelpers.nodeVisibility(errorBox, false);

        // Use CompletableFuture to handle async Google authentication
        GoogleAuthHelper.simulateGoogleLoginAsync()
            .thenCompose(googleUser -> {
                if (googleUser == null) {
                    throw new RuntimeException("Google login cancelled");
                }
                
                // Call backend Google login endpoint
                try {
                    UserDTO userDTO = ApiService.googleLogin(googleUser.getEmail(), googleUser.getName(), googleUser.getGoogleId());
                    return CompletableFuture.completedFuture(userDTO);
                } catch (Exception e) {
                    CompletableFuture<UserDTO> failedFuture = new CompletableFuture<>();
                    failedFuture.completeExceptionally(e);
                    return failedFuture;
                }
            })
            .thenAccept(userDTO -> {
                // Handle successful login on FX thread
                javafx.application.Platform.runLater(() -> {
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
                        UIHelpers.nodeVisibility(errorBox, true);
                        showError(errorBox, "Failed to load main page.");
                    }
                });
            })
            .exceptionally(throwable -> {
                // Handle errors on FX thread
                javafx.application.Platform.runLater(() -> {
                    UIHelpers.nodeVisibility(errorBox, true);
                    showError(errorBox, "Google login failed: " + throwable.getMessage());
                });
                return null;
            });
    }
}