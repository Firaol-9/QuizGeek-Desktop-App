package com.quiz_geek.controllers.student;

import com.quiz_geek.controllers.common.SceneManager;
import com.quiz_geek.controllers.common.SessionManager;
import com.quiz_geek.models.Assessment;
import com.quiz_geek.models.EvaluationResult;
import com.quiz_geek.services.core.UserService;
import com.quiz_geek.utils.UIHelpers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutControllerForStudents implements Initializable {

    @FXML StackPane rootPane;
    @FXML BorderPane borderPane;
    @FXML StackPane stackPane;
    @FXML StackPane centerContainer;

    @FXML ToggleButton dashboardButton;
    @FXML ToggleButton homeButton;
    @FXML ToggleButton myResultsButton;

    @FXML ImageView avatarImage;
    @FXML Label usernameLabel;

    private final ToggleGroup sidebarGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardButton.setToggleGroup(sidebarGroup);
        homeButton.setToggleGroup(sidebarGroup);
        myResultsButton.setToggleGroup(sidebarGroup);

        sidebarGroup.selectToggle(homeButton);
        sidebarGroup.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel == null && oldSel != null) {
                oldSel.setSelected(true);
            }
        });
        try{
            SceneManager.switchPage(centerContainer,"ForStudents/Home.fxml");
            HomeController controller = (HomeController) SceneManager.getController("ForStudents/Home.fxml");
            controller.setMainContainer(this);
        }catch (IOException e){
            e.printStackTrace();
        }

        usernameLabel.setText(UserService.getCurrentUser().getFullName());

        Image image = new Image(getClass().getResourceAsStream("/com/quiz_geek/images/avatar2.png"));
        avatarImage.setImage(image);

        avatarImage.setClip(new javafx.scene.shape.Circle(
                avatarImage.getFitWidth() / 2,
                avatarImage.getFitHeight() / 2,
                avatarImage.getFitWidth() / 2
        ));
    }

    @FXML
    public void showHome() throws IOException{
        SceneManager.switchPage(centerContainer, "ForStudents/Home.fxml");
    }

    public void showTakeAssessment(Assessment assessment){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/ForStudents/takeAssessment.fxml"));
            Parent takeAssessmentRoot = loader.load();

            TakeAssessmentController controller = loader.getController();
            controller.setParentController(this);
            controller.setupAssessment(assessment);
            controller.startAssessment();

            makeAssessmentAndResultContainerVisible();
            stackPane.getChildren().setAll(takeAssessmentRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAssessmentResult(String subject, EvaluationResult result) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/ForStudents/AssessmentResult.fxml"));
        Parent root = loader.load();

        AssessmentResultController controller = loader.getController();
        controller.setUp(subject, result);
        controller.setParentController(this);

        makeAssessmentAndResultContainerVisible();
        stackPane.getChildren().setAll(root);
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

        Stage stage = (Stage) homeButton.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        scene.setRoot(root);
        stage.setScene(scene);
    }



    public void makeMainLayoutVisible(){
        UIHelpers.nodeVisibility(stackPane, false);
        UIHelpers.nodeVisibility(borderPane, true);
    }

    public void makeAssessmentAndResultContainerVisible(){
        UIHelpers.nodeVisibility(stackPane, true);
        UIHelpers.nodeVisibility(borderPane, false);
    }
}