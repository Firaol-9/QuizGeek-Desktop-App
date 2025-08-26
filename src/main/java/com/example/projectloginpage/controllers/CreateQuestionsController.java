package com.example.projectloginpage.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateQuestionsController implements Initializable {
    @FXML
    private VBox questionsContainer;
    @FXML private Button addQuestionButton;
    @FXML private ChoiceBox<String> difficulty;
    @FXML private ChoiceBox<String> privacy;
    @FXML private TextField hourLimit;

    private int questionNumber = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        questionsContainer.getChildren().add(addQuestion());

        addQuestionButton.setOnAction(actionEvent -> {
            questionsContainer.getChildren().add(addQuestion());
        });

        //setting up the difficulty choicBox
        difficulty.getItems().setAll("Easy", "Medium", "Hard");
        difficulty.setValue("Medium");
        difficulty.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
        });

        //setting up the privacy choicBox
        privacy.getItems().setAll("Public", "Private");
        privacy.setValue("Public");
        privacy.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
        });
    }

    private VBox addQuestion(){
        VBox questionContainer;
        HBox questionAndButtonContainer, questionNumContainer, buttonContainer, option1, option2;
        TextArea textArea;
        Button removeButton;
        Label question;
        RadioButton radioButton;
        TextField textfield;
        ToggleGroup group;
        Hyperlink hyperlink;

        question = new Label("Question " + questionNumber);

        questionNumContainer = new HBox();
        questionNumContainer.setAlignment(Pos.TOP_LEFT);
        questionNumContainer.getChildren().add(question);
        HBox.setHgrow(question, Priority.ALWAYS);

        removeButton = new Button("Remove");

        buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.getChildren().add(removeButton);
        HBox.setHgrow(removeButton, Priority.ALWAYS);

        questionAndButtonContainer = new HBox();
        questionAndButtonContainer.setAlignment(Pos.TOP_LEFT);
        questionAndButtonContainer.getChildren().addAll(questionNumContainer, buttonContainer);
        HBox.setHgrow(questionNumContainer, Priority.ALWAYS);
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        textArea = new TextArea();
        textArea.setMinWidth(Region.USE_PREF_SIZE);
        textArea.setPrefHeight(100);
        textArea.setMaxWidth(Double.MAX_VALUE);

        group = new ToggleGroup();

        radioButton = new RadioButton();
        radioButton.setToggleGroup(group);

        textfield = new TextField("New option");

        option1 = addOption(group);
        option2 = addOption(group);

        hyperlink = new Hyperlink("Add options");

        questionContainer = new VBox();
        questionContainer.setAlignment(Pos.TOP_LEFT);
        questionContainer.setPadding(new Insets(5, 5, 5, 5));
        questionContainer.setSpacing(5);
        questionContainer.getChildren().addAll(questionAndButtonContainer, textArea, option1, option2, hyperlink);

        hyperlink.setOnAction(actionEvent -> {
            questionContainer.getChildren().add(questionContainer.getChildren().size() - 1, addOption(group));
        });

        removeButton.setOnAction(actionEvent -> {
            int size = questionsContainer.getChildren().size();
            if ( size > 1) questionsContainer.getChildren().remove(questionContainer);
            questionNumber--;
            updateQuestionNumber();
        });
        questionNumber++;
        updateQuestionNumber();
        return questionContainer;
    }

    private HBox addOption(ToggleGroup group){
        RadioButton radioButton = new RadioButton();
        TextField textfield = new TextField("New option");;
        HBox option = new HBox(5);;

        radioButton.setToggleGroup(group);

        option.setAlignment(Pos.TOP_LEFT);
        option.getChildren().addAll(radioButton, textfield);
        HBox.setHgrow(textfield, Priority.ALWAYS);

        return option;
    }

    private void updateQuestionNumber(){
        for (int i = 0; i < questionsContainer.getChildren().size(); i++) {
            VBox qContainer = (VBox) questionsContainer.getChildren().get(i);//questionContainer
            HBox QBContainer = (HBox) qContainer.getChildren().get(0);//questionAndButtonContainer
            HBox labelContainer = (HBox) QBContainer.getChildren().get(0);//QuestionNumberContainer
            Label label = (Label) labelContainer.getChildren().get(0);
            label.setText("Question " + (i + 1));
        }
    }
}
