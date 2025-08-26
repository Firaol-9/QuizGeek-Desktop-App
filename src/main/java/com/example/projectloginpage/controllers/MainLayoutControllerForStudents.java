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

public class MainLayoutControllerForStudents implements Initializable {

    @FXML BorderPane rootPane;
    @FXML StackPane centerContainer;

    @FXML ToggleButton dashboardButton;
    @FXML ToggleButton homeButton;
    @FXML ToggleButton myResultsButton;

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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void showHome() throws IOException{
        SceneManager.switchPage(centerContainer,"ForStudents/Home.fxml");
    }
}