package com.example.projectloginpage.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML TilePane tilePane;
    @FXML ToggleButton practiceQuestionsTab;
    @FXML ToggleButton worksheetsTab;
    @FXML ToggleButton examsTab;

    private final ToggleGroup navGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tilePane.setPrefTileWidth(-1);//-1 is to make the tilePane automatically adjust the width and height of the cards
        tilePane.setPrefTileHeight(-1);
        tilePane.setHgap(25);
        tilePane.setVgap(25);

        addCards("biology");

        practiceQuestionsTab.setToggleGroup(navGroup);
        worksheetsTab.setToggleGroup(navGroup);
        examsTab.setToggleGroup(navGroup);

        navGroup.selectToggle(practiceQuestionsTab);

        navGroup.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel == null && oldSel != null) {
                oldSel.setSelected(true);
            }
        });
    }

    void addCards(String subject){
        for (int i = 0; i < 20; i++){
            Label subjectName = new Label( subject);
            subjectName.getStyleClass().add("subject-label");

            Image img = new Image(getClass().getResource("/com/example/projectloginpage/images/"+ subject + ".jpg").toExternalForm(), 232, 180, true, true);
            ImageView imageView = new ImageView(img);

            Rectangle clip = new Rectangle(232, 180);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            imageView.setClip(clip);

            StackPane pane = new StackPane();
            pane.getStyleClass().add("subject-image");
            pane.getChildren().add(imageView);

            VBox card = new VBox();
            card.getChildren().addAll(pane, subjectName);
            card.setSpacing(10);
            card.getStyleClass().add("card");
            card.setPrefHeight(180);
            card.setPrefWidth(200);

            tilePane.getChildren().add(card);
        }
    }
    @FXML
    void showPracticeQuestions(){
        tilePane.getChildren().clear();
        addCards("biology");
    }
    @FXML
    void showWorkSheets(){
        tilePane.getChildren().clear();
        addCards("physics");
    }

    @FXML
    void showExams(){
        tilePane.getChildren().clear();
        addCards("chemistry");
    }
}
