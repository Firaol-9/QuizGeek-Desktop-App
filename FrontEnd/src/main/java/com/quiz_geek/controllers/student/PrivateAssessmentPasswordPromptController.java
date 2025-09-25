package com.quiz_geek.controllers.student;

import com.quiz_geek.utils.UIHelpers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PrivateAssessmentPasswordPromptController {

    @FXML TextField passwordTextfield;
    @FXML Button submitButton;
    @FXML Button cancelButton;
    @FXML Label errorLabel;
    @FXML HBox errorLabelContainer;
    @FXML Label describtionLabel;

    private String correctPassword;
    private Runnable onSuccess; // callback when password matches

    @FXML
    void initialize(){
        UIHelpers.nodeVisibility(errorLabelContainer, false);
    }

    public void setDetails(String assessmentName, String correctPassword, Runnable onSuccess) {
        this.correctPassword = correctPassword;
        this.onSuccess = onSuccess;
        describtionLabel.setText("The assessment " + assessmentName + " is private. Please enter the password to continue.");
        describtionLabel.setWrapText(true);
        describtionLabel.setPrefWidth(300);
    }

    @FXML
    private void checkPassword() {
        String entered = passwordTextfield.getText();
        if (entered != null && entered.equals(correctPassword)) {
            close();
            if (onSuccess != null) onSuccess.run();
        } else {
            errorLabel.setText("Incorrect password. Try again.");
            UIHelpers.nodeVisibility(errorLabelContainer, true);
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
