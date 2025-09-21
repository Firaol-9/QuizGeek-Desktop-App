package com.quiz_geek.controllers.teacher;

import com.quiz_geek.controllers.common.SceneManager;
import com.quiz_geek.exceptions.InvalidAssessmentException;
import com.quiz_geek.exceptions.InvalidQuestionException;
import com.quiz_geek.models.Question;
import com.quiz_geek.models.QuestionsAccessibility;
import com.quiz_geek.models.QuestionsDifficulty;
import com.quiz_geek.models.QuestionsType;
import com.quiz_geek.services.teacher.TeacherAssessmentService;
import com.quiz_geek.services.core.TextFieldUtils;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.UIHelpers;

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
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateQuestionsController implements Initializable {
    @FXML private VBox questionsContainer;
    @FXML private Button addQuestionButton;
    @FXML private Button createButton;
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
    private final int textAreaIndex = 1;//index of text area in its container
    private final int optionsRadioButtonIndex = 0;//index of the radio button in its container
    private final int optionsTextFieldIndex = 1;//index of the options textField in its container
    private final int optionsContainerIndex = 2;////index of the options container in its container

    boolean isExamSelected = true, isPrivateSelected = false;

    QuestionsDifficulty defaultDifficulty = QuestionsDifficulty.MEDIUM;
    QuestionsType defaultType = QuestionsType.EXAM;
    QuestionsAccessibility defaultAccessibility = QuestionsAccessibility.PUBLIC;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        questionsContainer.getChildren().add(addQuestionContainer());

        addQuestionButton.setOnAction(actionEvent -> questionsContainer.getChildren().add(addQuestionContainer()));

        //setting up the difficulty choiceBox
        difficultyChoiceBox.getItems().addAll(QuestionsDifficulty.values());
        difficultyChoiceBox.setValue(defaultDifficulty);
        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {});

        //setting up the privacy choiceBox
        accessibilityChoiceBoxChoiceBox.getItems().addAll(QuestionsAccessibility.values());
        accessibilityChoiceBoxChoiceBox.setValue(defaultAccessibility);
        UIHelpers.nodeVisibility(timeLimitAndPasswordContainer, isPrivateSelected);
        UIHelpers.nodeVisibility(passwordContainer, isPrivateSelected);
        accessibilityChoiceBoxChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == QuestionsAccessibility.PRIVATE){
                UIHelpers.nodeVisibility(timeLimitAndPasswordContainer,true);
                UIHelpers.nodeVisibility(passwordContainer,true);
                isPrivateSelected = true;
            }else{
                if (!isExamSelected){
                    UIHelpers.nodeVisibility(timeLimitAndPasswordContainer, false);
                }
                UIHelpers.nodeVisibility(passwordContainer, false);
                isPrivateSelected = false;
            }
        });

        questionsTypeChoiceBox.getItems().addAll(QuestionsType.values());
        questionsTypeChoiceBox.setValue(defaultType);
        UIHelpers.nodeVisibility(timeLimitAndPasswordContainer, isExamSelected);
        UIHelpers.nodeVisibility(timeLimitContainer, isExamSelected);
        questionsTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if ( newValue == QuestionsType.EXAM){
                UIHelpers.nodeVisibility(timeLimitAndPasswordContainer, true);
                UIHelpers.nodeVisibility(timeLimitContainer, true);
                isExamSelected = true;
            }else{
                if (!isPrivateSelected){
                    UIHelpers.nodeVisibility(timeLimitAndPasswordContainer, false);
                }
                UIHelpers.nodeVisibility(timeLimitContainer, false);
                isExamSelected = false;
            }
        });

        //makes the textField only to accept numbers
        TextFieldUtils.makeNumeric(timeLimitTextField);

        //the errorBox is inVisible by default
        UIHelpers.nodeVisibility(errorBox, false);

        //loading icons to buttons
        try{
            addQuestionButton.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/add.svg", 24, 24, Color.WHITE));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void createQuestion() {

        clearErrors();
        List<Question> validQuestions = getValidQuestions(questionsContainer);

        MyQuestionsController controller = (MyQuestionsController) SceneManager.getController("ForTeachers/MyQuestions.fxml");

        if ( questionsContainer.getChildren().size() > validQuestions.size()){
            showError("\nPlease fix all the above problems to save the assessment.");
            return;
        }

        try{
            String title = quizTitleTextField.getText();
            String subjectName = subjectTextField.getText();
            QuestionsType type = questionsTypeChoiceBox.getValue();
            QuestionsAccessibility accessibility = accessibilityChoiceBoxChoiceBox.getValue();
            QuestionsDifficulty difficulty = difficultyChoiceBox.getValue();
            String timeLimit = timeLimitTextField.getText();
            String password = passwordTextField.getText();

            TeacherAssessmentService.addAssessment(
                    quizTitleTextField.getText(),
                    subjectTextField.getText(),
                    validQuestions,
                    difficultyChoiceBox.getValue(),
                    questionsTypeChoiceBox.getValue(),
                    accessibilityChoiceBoxChoiceBox.getValue(),
                    timeLimitTextField.getText(),
                    passwordTextField.getText()
            );
            controller.addAssessmentCard(TeacherAssessmentService.getInstance().getAllAssessments().getLast());
            reNewPage();
        }
        catch(InvalidAssessmentException e){
            showError(e.getMessage());
        }
    }

    private VBox addQuestionContainer(){
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
        try{
            removeButton.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/cancel.svg", 24, 24, Color.WHITE));
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
        optionsContainer.setSpacing(5);

        hyperlink = new Hyperlink("Add options");

        questionContainer = new VBox();
        questionContainer.setAlignment(Pos.TOP_LEFT);
        questionContainer.setPadding(new Insets(5, 5, 5, 5));
        questionContainer.setSpacing(5);
        questionContainer.getChildren().addAll(questionAndButtonContainer, textArea, optionsContainer, hyperlink);
        questionContainer.getStyleClass().add("section-card");
        questionContainer.getStyleClass().add("section-card-primaryColor");

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
        TextField textfield = new TextField();
        HBox option = new HBox(5);
        Button removeOption = new Button();

        removeOption.getStyleClass().add("removeOptionButton");
        removeOption.setAlignment(Pos.CENTER);
        try{
            removeOption.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/remove.svg", 20, 24, Color.WHITE));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        textfield.setPromptText("New option");

        radioButton.setToggleGroup(group);
        HBox.setMargin(radioButton, new Insets(8, 0, 0, 0));

        option.setAlignment(Pos.CENTER_LEFT);
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
            HBox QBContainer = (HBox) qContainer.getChildren().getFirst();//questionAndButtonContainer
            HBox labelContainer = (HBox) QBContainer.getChildren().getFirst();//QuestionNumberContainer
            Label label = (Label) labelContainer.getChildren().getFirst();
            label.setText("Question " + (i + 1));
        }
    }

    private Question extractQuestion(VBox questionContainer){
        TextArea textArea = (TextArea) questionContainer.getChildren().get(textAreaIndex);
        String questionText = textArea.getText();

        VBox optionsContainer = (VBox) questionContainer.getChildren().get(optionsContainerIndex);
        List<String> optionsList = extractOptions(optionsContainer);
        String answer = getAnswer(optionsContainer);

        return new Question(questionText, optionsList, answer);
    }

    private List<Question> getValidQuestions(VBox questionsContainer){
        List<Question> validQuestions = new ArrayList<>();
        for (int i = 0; i < questionsContainer.getChildren().size(); i++) {
            Question q = extractQuestion((VBox)questionsContainer.getChildren().get(i));

            clearError((VBox) questionsContainer.getChildren().get(i));

            try{
                q.validate();
                validQuestions.add(q);
            }
            catch (InvalidQuestionException e){
                markError((VBox) questionsContainer.getChildren().get(i), e.getMessage());
                showError("Error in Question " + (i +1) + ": " + e.getMessage());
            }
        }
        return validQuestions;
    }

    private List<String> extractOptions(VBox optionsContainer){
        HBox optionContainer;
        TextField textField;
        String optionText;
        List<String> optionsList = new ArrayList<>();

        for (int i = 0; i < optionsContainer.getChildren().size(); i++) {
            optionContainer = (HBox) optionsContainer.getChildren().get(i);
            textField = (TextField) optionContainer.getChildren().get(optionsTextFieldIndex);
            optionText = textField.getText();
            optionsList.add(optionText);
        }
        return optionsList;
    }

    private String getAnswer(VBox optionsContainer){

        for (int i = 0; i < optionsContainer.getChildren().size(); i++) {
            HBox optionContainer = (HBox) optionsContainer.getChildren().get(i);
            RadioButton radioButton = (RadioButton) optionContainer.getChildren().get(optionsRadioButtonIndex);
            TextField textField = (TextField) optionContainer.getChildren().get(optionsTextFieldIndex);

            if (radioButton.isSelected()) return textField.getText();
        }
        return null;
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

    private void clearError(VBox container) {
        container.getStyleClass().remove("error-field");
        Tooltip.uninstall(container, null); // removes any tooltip
    }

    private void markError(VBox container, String message) {
        if (!container.getStyleClass().contains("error-field")){
            container.getStyleClass().add("error-field");
        }
        Tooltip.install(container, new Tooltip(message));
    }

    private void reNewPage(){
        quizTitleTextField.clear();
        subjectTextField.clear();
        timeLimitTextField.clear();
        passwordTextField.clear();
        difficultyChoiceBox.setValue(defaultDifficulty);
        questionsTypeChoiceBox.setValue(defaultType);
        accessibilityChoiceBoxChoiceBox.setValue(defaultAccessibility);
        questionNumber -= questionsContainer.getChildren().size() + 1;
        questionsContainer.getChildren().remove(1, questionsContainer.getChildren().size());
        updateQuestionNumber();

        VBox questionContainer = (VBox) questionsContainer.getChildren().getFirst();
        TextArea textArea = (TextArea) questionContainer.getChildren().get(textAreaIndex);
        textArea.clear();
        VBox options = (VBox) questionContainer.getChildren().get(optionsContainerIndex);
        options.getChildren().remove(2, options.getChildren().size());
        for(Node node: options.getChildren()){
            HBox option = (HBox) node;
            RadioButton btn = (RadioButton) option.getChildren().get(optionsRadioButtonIndex);
            btn.setSelected(false);
            TextField field = (TextField) option.getChildren().get(optionsTextFieldIndex);
            field.clear();
        }
    }
}