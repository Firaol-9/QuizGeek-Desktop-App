package com.quiz_geek.controllers.teacher;

import com.quiz_geek.controllers.common.SceneManager;
import com.quiz_geek.controllers.common.SessionManager;
import com.quiz_geek.services.core.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutControllerForTeachers implements Initializable {

    @FXML BorderPane rootPane;
    @FXML StackPane centerContainer;

    private final ToggleGroup sideBarGroup = new ToggleGroup();;

    @FXML ToggleButton dashboardButton;
    @FXML ToggleButton myQuestionsButton;
    @FXML ToggleButton createQuestionsButton;
    @FXML ToggleButton manageStudentsButton;

    @FXML ImageView avatarImage;
    @FXML Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dashboardButton.setToggleGroup(sideBarGroup);
        myQuestionsButton.setToggleGroup(sideBarGroup);
        createQuestionsButton.setToggleGroup(sideBarGroup);
        manageStudentsButton.setToggleGroup(sideBarGroup);

        sideBarGroup.selectToggle(myQuestionsButton);

        sideBarGroup.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel == null && oldSel != null) {
                oldSel.setSelected(true);
            }
        });

        try {
            SceneManager.switchPage(centerContainer, "ForTeachers/MyQuestions.fxml");
        }catch(IOException e){
            e.printStackTrace();
        }

        usernameLabel.setText(UserService.getCurrentUser().getFullName());

        Image image = new Image(getClass().getResourceAsStream("/com/quiz_geek/images/avatar.png"));
        avatarImage.setImage(image);

        avatarImage.setClip(new javafx.scene.shape.Circle(
                avatarImage.getFitWidth() / 2,
                avatarImage.getFitHeight() / 2,
                avatarImage.getFitWidth() / 2
        ));
    }

    @FXML
    private void showLogoutConfirmation() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleLogout();
        }
    }

    private void handleLogout() throws IOException {
        SessionManager.deleteToken();
        UserService.getInstance().setCurrentUser(null);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/common/Login.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) myQuestionsButton.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        scene.setRoot(root);
        stage.setScene(scene);
    }

    public void setUserAvatar(File file) {
        Image userImage = new Image(file.toURI().toString());
        avatarImage.setImage(userImage);
    }

    @FXML
    void showMyQuestions() throws IOException {
        SceneManager.switchPage(centerContainer, "ForTeachers/MyQuestions.fxml");
    }

    @FXML
    void showCreateQuestions() throws IOException{
        SceneManager.switchPage(centerContainer, "ForTeachers/CreateQuestions.fxml");
    }
}