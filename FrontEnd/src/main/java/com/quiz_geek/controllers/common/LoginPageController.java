package com.quiz_geek.controllers.common;

import com.quiz_geek.exceptions.EmailNotFoundException;
import com.quiz_geek.exceptions.IncorrectPasswordException;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.services.core.UserService;

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
        userService.addUser();
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
    void linkToMainPage(ActionEvent event) throws IOException{

        //clear the error labels in the Vboxes if there are any
        emailErrorVbox.getChildren().clear();
        incorrectPasswordErrorVbox.getChildren().clear();

        String email = emailTextField.getText();
        String password = passwordTextField.getText();


        try {
            userService.login(email, password);

            String fxmlPath;
            if (UserService.getCurrentUser().getRole() == UserRole.STUDENT)
                fxmlPath = "ForStudents/MainLayoutForStudents.fxml";
            else
                fxmlPath = "ForTeachers/MainLayoutForTeachers.fxml";

            Parent root = SceneManager.getPage(fxmlPath);

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
        errorLabel.getStyleClass().addAll("label-errorRed", "label-verySmall");
        vbox.getChildren().add(errorLabel);
    }
}