package com.example.projectloginpage.controllers;

import com.example.projectloginpage.exceptions.InvalidQuestionException;
import com.example.projectloginpage.models.QuestionsDifficulty;
import com.example.projectloginpage.models.QuestionsAccessibility;
import com.example.projectloginpage.models.QuestionsType;
import com.example.projectloginpage.models.Question;
import com.example.projectloginpage.models.Assessment;
import com.example.projectloginpage.services.TextFieldUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateQuestionsController implements Initializable {
    @FXML private VBox questionsContainer;
    @FXML private Button addQuestionButton;
    @FXML private TextField quizTitleTextField;
    @FXML private TextField subjectTextField;

    @FXML private ChoiceBox<QuestionsDifficulty> difficultyChoiceBox;
    @FXML private ChoiceBox<QuestionsAccessibility> accessibilityChoiceBoxChoiceBox;
    @FXML private ChoiceBox<QuestionsType> questionsTypeChoiceBox;

    @FXML private HBox timeLimitAndPasswordContainer;
    @FXML private VBox timeLimitContainer;
    @FXML private VBox passwordContainer;
    @FXML private TextField passwordTextField;
    @FXML private TextField timeLimitTextField;
    @FXML private VBox errorBox;

    private int questionNumber = 1;

    List<Question> validQuestions = new ArrayList<>();
    Assessment assessment;

    boolean isExamSelected = true, isPrivateSelected = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        questionsContainer.getChildren().add(addQuestion());

        addQuestionButton.setOnAction(actionEvent -> questionsContainer.getChildren().add(addQuestion()));

        //setting up the difficulty choiceBox
        difficultyChoiceBox.getItems().addAll(QuestionsDifficulty.values());
        difficultyChoiceBox.setValue(QuestionsDifficulty.MEDIUM);
        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {});

        //setting up the privacy choiceBox
        accessibilityChoiceBoxChoiceBox.getItems().addAll(QuestionsAccessibility.values());
        accessibilityChoiceBoxChoiceBox.setValue(QuestionsAccessibility.PUBLIC);
        timeLimitAndPasswordContainer.setVisible(isPrivateSelected);
        timeLimitAndPasswordContainer.setManaged(isPrivateSelected);
        passwordContainer.setVisible(isPrivateSelected);
        passwordContainer.setManaged(isPrivateSelected);
        accessibilityChoiceBoxChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == QuestionsAccessibility.PRIVATE){
                timeLimitAndPasswordContainer.setVisible(true);
                timeLimitAndPasswordContainer.setManaged(true);
                passwordContainer.setVisible(true);
                passwordContainer.setManaged(true);
                isPrivateSelected = true;
            }else{
                if (!isExamSelected){
                    timeLimitAndPasswordContainer.setVisible(false);
                    timeLimitAndPasswordContainer.setManaged(false);
                }
                passwordContainer.setVisible(false);
                passwordContainer.setManaged(false);
                isPrivateSelected = false;
            }
        });
        questionsTypeChoiceBox.getItems().addAll(QuestionsType.values());
        questionsTypeChoiceBox.setValue(QuestionsType.EXAM);
        timeLimitAndPasswordContainer.setVisible(isExamSelected);
        timeLimitAndPasswordContainer.setManaged(isExamSelected);
        timeLimitContainer.setVisible(isExamSelected);
        timeLimitContainer.setManaged(isExamSelected);
        questionsTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if ( newValue == QuestionsType.EXAM){
                timeLimitAndPasswordContainer.setVisible(true);
                timeLimitAndPasswordContainer.setManaged(true);
                timeLimitContainer.setVisible(true);
                timeLimitContainer.setManaged(true);
                isExamSelected = true;
            }else{
                if (!isPrivateSelected){
                    timeLimitAndPasswordContainer.setVisible(false);
                    timeLimitAndPasswordContainer.setManaged(false);
                }
                timeLimitContainer.setVisible(false);
                timeLimitContainer.setManaged(false);
                isExamSelected = false;
            }
        });

        //makes the textField only to accept numbers
        TextFieldUtils.makeNumeric(timeLimitTextField);

        //the errorBox is inVisible by default
        errorBox.setVisible(false);
        errorBox.setManaged(false);
    }

    @FXML
    private void createQuestion() {

        clearErrors();// clear previous errors
        validQuestions.clear();// clear old valid questions

        String quizTitle = quizTitleTextField.getText();
        if (quizTitle.trim().isEmpty()){
            markError(quizTitleTextField);
            showError("Quiz title is empty.");
            return;
        }else{
            clearError(quizTitleTextField);
        }

        String quizSubject = subjectTextField.getText();
        if(quizSubject.trim().isEmpty()){
            markError(subjectTextField);
            showError("Subject field is empty.");
            return;
        }else{
            clearError(subjectTextField);
        }

        for (int i = 0; i < questionsContainer.getChildren().size(); i++) {

            VBox questionContainer = (VBox) questionsContainer.getChildren().get(i);

            List<String> errors = validateQuestion(questionContainer, i);

            if(!errors.isEmpty()){
                showError(String.join("\n", errors));
                continue;
            }

            Question question = getQuestion(questionContainer);

            try {
                question.validate();
                validQuestions.add(question);
            } catch (InvalidQuestionException e) {
                showError("Question " + (i + 1) + ": " + e.getMessage());
            }
        }

        String timeLimitStr = timeLimitTextField.getText();
        int timeLimit = Integer.parseInt(timeLimitStr);

        if (isExamSelected && timeLimitStr.trim().isEmpty()){
            markError(timeLimitTextField);
            showError("Time-limit field is empty.");
            return;
        }else{
            clearError(timeLimitTextField);
        }

        String password = passwordTextField.getText();
        if (isPrivateSelected && password.trim().isEmpty()){
            markError(passwordTextField);
            showError("Password field is empty.");
            return;
        }else{
            clearError(passwordTextField);
        }

        if ( questionsContainer.getChildren().size() > validQuestions.size()) return;

        QuestionsDifficulty difficulty = difficultyChoiceBox.getValue();
        QuestionsType type = questionsTypeChoiceBox.getValue();
        QuestionsAccessibility accessibility = accessibilityChoiceBoxChoiceBox.getValue();

        if(isExamSelected && isPrivateSelected){
            assessment = new Assessment(quizTitle, quizSubject, validQuestions, difficulty, type, accessibility, timeLimit, password);
        }
        else if(isExamSelected && !isPrivateSelected){
            assessment = new Assessment(quizTitle, quizSubject, validQuestions, difficulty, type, accessibility, timeLimit);
        }
        else if( !isExamSelected && isPrivateSelected){
            assessment = new Assessment(quizTitle, quizSubject, validQuestions, difficulty, type, accessibility, password);
        }else{
            assessment = new Assessment(quizTitle, quizSubject, validQuestions, difficulty, type, accessibility);
        }
    }

    private VBox addQuestion(){
        VBox questionContainer, optionsContainer;
        HBox questionAndButtonContainer, questionNumContainer, buttonContainer, option1, option2;
        TextArea textArea;
        Button removeButton;
        Label question;
        RadioButton radioButton;
        ToggleGroup group;
        Hyperlink hyperlink;

        question = new Label("Question " + questionNumber);

        questionNumContainer = new HBox();
        questionNumContainer.setAlignment(Pos.TOP_LEFT);
        questionNumContainer.getChildren().add(question);
        HBox.setHgrow(question, Priority.ALWAYS);

        removeButton = new Button("Remove");
        removeButton.getStyleClass().add("removeOptionButton");

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

        optionsContainer = new VBox();

        option1 = addOption(group);
        option2 = addOption(group);

        optionsContainer.getChildren().addAll(option1, option2);

        hyperlink = new Hyperlink("Add options");

        questionContainer = new VBox();
        questionContainer.setAlignment(Pos.TOP_LEFT);
        questionContainer.setPadding(new Insets(5, 5, 5, 5));
        questionContainer.setSpacing(5);
        questionContainer.getChildren().addAll(questionAndButtonContainer, textArea, optionsContainer, hyperlink);
        questionContainer.getStyleClass().add("section-card");

        hyperlink.setOnAction(actionEvent -> optionsContainer.getChildren().add(addOption(group)));

        removeButton.setOnAction(actionEvent -> {
            int size = questionsContainer.getChildren().size();
            if ( size > 1) {
                questionsContainer.getChildren().remove(questionContainer);
                questionNumber--;
            }
            updateQuestionNumber();
        });
        questionNumber++;
        updateQuestionNumber();
        questionContainer.setUserData(group);
        return questionContainer;
    }

    private HBox addOption(ToggleGroup group){
        RadioButton radioButton = new RadioButton();
        TextField textfield = new TextField("New option");
        HBox option = new HBox(5);
        Button removeOption = new Button("X");

        removeOption.getStyleClass().add("removeOptionButton");
        HBox.setMargin(removeOption, new Insets(8, 0, 0, 0));

        radioButton.setToggleGroup(group);
        HBox.setMargin(radioButton, new Insets(8, 0, 0, 0));

        option.setAlignment(Pos.TOP_LEFT);
        option.getChildren().addAll(radioButton, textfield, removeOption);
        HBox.setHgrow(textfield, Priority.ALWAYS);

        removeOption.setOnAction(e -> {
            if (option.getParent() instanceof Pane parent) {
                //if the optionsContainer contains at least two options
                if( parent.getChildren().size() > 2) parent.getChildren().remove(option);
            }
        });

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

    private Question getQuestion(VBox questionContainer){
        TextArea textArea = (TextArea) questionContainer.getChildren().get(1);
        String questionText = textArea.getText();

        VBox optionsContainer = (VBox) questionContainer.getChildren().get(2);
        List<String> optionsList = getOptions(optionsContainer);
        int answerIndex = getAnswerIndex(optionsContainer);

        return new Question(questionText, optionsList, answerIndex);
    }

    private List<String> getOptions(VBox optionsContainer){
        HBox optionContainer;
        TextField textField;
        String optionText;
        List<String> optionsList = new ArrayList<>();

        for (int i = 0; i < optionsContainer.getChildren().size(); i++) {
            optionContainer = (HBox) optionsContainer.getChildren().get(i);
            textField = (TextField) optionContainer.getChildren().get(1);
            optionText = textField.getText();
            optionsList.add(optionText);
        }
        return optionsList;
    }

    private int getAnswerIndex(VBox optionsContainer){
        HBox optionContainer;
        RadioButton radioButton;
        for (int i = 0; i < optionsContainer.getChildren().size(); i++) {
            optionContainer = (HBox) optionsContainer.getChildren().get(i);
            radioButton = (RadioButton) optionContainer.getChildren().getFirst();
            if (radioButton.isSelected()) return i;
        }
        return -1;
    }

    private List<String> validateQuestion(VBox questionContainer, int i){
        List<String> errors = new ArrayList<>();
        //question text
        TextArea questionField = (TextArea) questionContainer.getChildren().get(1);
        if (isQuestionEmpty(questionContainer)) {
            markError(questionField);
            errors.add("Question " + (i + 1) + ": missing text");
        } else {
            clearError(questionField);
        }
        //question options
        VBox optionsContainer = (VBox) questionContainer.getChildren().get(2);
        boolean optionsEmpty = false;
        for (Node optionNode : optionsContainer.getChildren()) {
            TextField optionField = (TextField) ((HBox) optionNode).getChildren().get(1);
            if (optionField.getText().trim().isEmpty()) {
                markError(optionField);
                optionsEmpty = true;
            } else {
                clearError(optionField);
            }
        }
        if (optionsEmpty) {
            errors.add("Question " + (i + 1) + ": some options are empty");
        }
        //right answer selected
        if (!isTheAnswerSelected(questionContainer)) {
            errors.add("Question " + (i + 1) + ": no answer selected");
            markParentError(optionsContainer);
        } else {
            clearParentError(optionsContainer);
        }

        return errors;
    }

    private boolean isQuestionEmpty(VBox questionContainer){

        TextArea textArea = (TextArea) questionContainer.getChildren().get(1);
        return textArea.getText().trim().isEmpty();
    }

    private boolean isTheAnswerSelected(VBox questionContainer){
        ToggleGroup group = (ToggleGroup) questionContainer.getUserData();
        return group.getSelectedToggle() != null;
    }

    private void showError(String message){
        //make the errorBox visible first
        if (!errorBox.isVisible()){
            errorBox.setVisible(true);
            errorBox.setManaged(true);//
        }
        Label label = new Label(message);
        label.getStyleClass().add("error-label");
        errorBox.getChildren().add(label);
    }

    private void clearErrors() {
        errorBox.getChildren().clear();
        errorBox.setVisible(false);
        errorBox.setManaged(false);
    }

    private void markError(Control field) {
        field.getStyleClass().add("error-field");
    }

    private void clearError(Control field) {
        field.getStyleClass().remove("error-field");
    }

    private void markParentError(Region parent) {
        parent.getStyleClass().add("error-field");
    }

    private void clearParentError(Region parent) {
        parent.getStyleClass().remove("error-field");
    }
}