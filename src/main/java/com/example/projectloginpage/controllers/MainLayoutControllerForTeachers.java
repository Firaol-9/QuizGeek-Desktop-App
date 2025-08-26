package com.example.projectloginpage.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutControllerForTeachers implements Initializable {

    @FXML BorderPane rootPane;
    @FXML StackPane centerContainer;

    private final ToggleGroup sideBarGroup = new ToggleGroup();;

    @FXML ToggleButton dashboardButton;
    @FXML ToggleButton myQuestionsButton;
    @FXML ToggleButton createQuestionsButton;
    @FXML ToggleButton manageStudentsButton;

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