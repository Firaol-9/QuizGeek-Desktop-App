package com.quiz_geek.controllers.student;

import com.quiz_geek.models.*;
import com.quiz_geek.services.core.UserService;
import com.quiz_geek.services.student.StudentAssessmentService;
import com.quiz_geek.utils.Constants;
import com.quiz_geek.utils.ExamTimer;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.Helpers;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TakeAssessmentController {
    @FXML VBox mainContainer;
    @FXML VBox mainQuestionContainer;
    @FXML HBox titleContainer;
    @FXML HBox timerContainer;
    @FXML TilePane questionNumberContainer;
    @FXML Button previousButton;
    @FXML HBox totalQuestionNumberContainer;
    @FXML Button finishButton;
    @FXML Button nextButton;

    Assessment assessment;

    private int currentQuestionIndex = 0;
    private int currentQuestionNumber = 1;
    private Label questionNumber = new Label();
    private boolean isExam = false;

    List<VBox> questionsContainer = new ArrayList<>();

    MainLayoutControllerForStudents parentController;

    ExamTimer timer = new ExamTimer();

    int questionTextContainerIndex = 1;//the index of question text container in the question container
    int optionsContainerIndex = 2;//the index of options container in the question container

    @FXML
    private void initialize(){
        currentQuestionNumber = 1;
        currentQuestionIndex = 0;
        isExam = false;

        questionNumberContainer.setVgap(16);
        questionNumberContainer.setHgap(16);

        totalQuestionNumberContainer.getChildren().add(questionNumber);

        questionNumber.getStyleClass().add("label-large");
        questionNumber.getStyleClass().add("label-bold");
    }

    private void showQuestion(int index){
        questionNumber.setText((index + 1) + "/" + questionsContainer.size());
        mainQuestionContainer.getChildren().clear();
        mainQuestionContainer.getChildren().add(questionsContainer.get(index));

        if (index == questionsContainer.size() - 1){
            Helpers.nodeVisibility(nextButton, false);
            Helpers.nodeVisibility(finishButton, true);
        }
        else{
            Helpers.nodeVisibility(nextButton, true);
            Helpers.nodeVisibility(finishButton, false);
        }
    }

    private Button addQuestionNumberButton(int n){
        Button btn = new Button();

        btn.setPrefWidth(47);
        btn.setPrefHeight(50);
        btn.getStyleClass().add("button-questionNumber");
        btn.setText("" + n);

        btn.setOnAction(event->{
            currentQuestionIndex = n - 1;
            showQuestion(n-1);
            }
         );
        return btn;
    }

    public void startAssessment(){
        showQuestion(0);
        if (isExam){
            timer.start();
            timer.setOnTimeUp(this::quitAndSaveAssessment);// equivalent to timer.setOnTimeUp( ()-> quitAndSaveAssessment());
        }
    }

    public void setupAssessment(Assessment assessment){
        this.assessment = assessment;
        Label title = new Label (assessment.getAssessmentTitle());
        Label timerLabel = new Label ();

        isExam = assessment instanceof Exam;

        title.getStyleClass().addAll("label-white", "label-large", "label-bold");
        timerLabel.getStyleClass().addAll("label-white","label-large", "label-bold" );

        try{
            timerLabel.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/timer.svg", 28, 28, Color.WHITE));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if ( !isExam) Helpers.nodeVisibility(timerLabel, false);
        else{
            Exam exam = (Exam) assessment;
            timer.setTimerLabel(timerLabel);
            timer.setTime(exam.getTimeLimitInMinutes());
            Helpers.nodeVisibility(timerLabel, true);
        }

        titleContainer.getChildren().add(title);
        timerContainer.getChildren().add(timerLabel);

        for(int i = 0; i<  assessment.getQuestions().size(); i++){
            questionsContainer.add(addQuestionContainer(assessment.getQuestions().get(i)));
            questionNumberContainer.getChildren().add(addQuestionNumberButton(i + 1));

            HBox flagContainer = (HBox) questionsContainer.get(i).getChildren().getFirst();
            ToggleButton flagButton = (ToggleButton) flagContainer.getChildren().getFirst();

            Button numberButton = (Button) questionNumberContainer.getChildren().get(i);
            //fills the numberButton red when the corresponding question's container flag button is selected
            flagButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) numberButton.getStyleClass().add("button-questionNumber-flagged");
                else{
                    if (numberButton.getStyleClass().contains("button-questionNumber-flagged")) numberButton.getStyleClass().remove("button-questionNumber-flagged");
                }
            });
        }
    }

    //method for adding a question to container
    private VBox addQuestionContainer(Question question){
        VBox questionContainer = new VBox();//the container which contains questions and options
        VBox questionTextContainer = new VBox();//the container which contains the question only
        Label questionText = new Label(currentQuestionNumber + ". " + question.getQuestionText());
        HBox flagContainer = new HBox();
        VBox optionsContainer = addOptions(question);//the container which contains the options
        Hyperlink link = new Hyperlink("Clear my answer");
        ToggleButton flagButton = new ToggleButton();

        //some properties of the container
        VBox.setVgrow(questionContainer, Priority.ALWAYS);
        questionContainer.setPrefWidth(800);
        questionContainer.setPrefHeight(400);
        questionContainer.setMaxWidth(1000);
        questionContainer.getStyleClass().addAll("section-card", "section-card-questionContainer");
        questionContainer.setSpacing(16);

        optionsContainer.setPrefWidth(1000);
        optionsContainer.setSpacing(16);
        VBox.setVgrow(optionsContainer, Priority.ALWAYS);

        //flagButton
        try{
            ImageView normalIcon = SvgLoader.loadSvg("/com/quiz_geek/Icons/flag.svg", 30, 28, Constants.errorColor);
            ImageView activeIcon = SvgLoader.loadSvg("/com/quiz_geek/Icons/flag.svg", 30, 28, Color.WHITE);

            flagButton.setGraphic(normalIcon);

            flagButton.selectedProperty().addListener((obs, wasSelected, isSelected) ->
                flagButton.setGraphic(isSelected ? activeIcon : normalIcon)
            );

        }catch(Exception e){
            e.printStackTrace();
        }
        flagButton.getStyleClass().add("toggle-button-flag");
        flagButton.setPrefWidth(45);
        flagButton.setPrefHeight(37);
        flagContainer.setAlignment(Pos.TOP_RIGHT);
        flagContainer.getChildren().add(flagButton);

        questionText.getStyleClass().addAll("label-black", "label-large", "label-bold");
        questionTextContainer.getChildren().add(questionText);

        link.setOnAction(event ->{//once the link is pressed it unselects the option choose
            RadioButton button = (RadioButton) optionsContainer.getChildren().getFirst();
            ToggleGroup optionGroup = button.getToggleGroup();
            optionGroup.selectToggle(null);
        });

        questionContainer.getChildren().addAll(flagContainer, questionTextContainer, optionsContainer, link);
        currentQuestionNumber++;

        return questionContainer;
    }

    private VBox addOptions(Question question){
        VBox optionsContainer = new VBox();//the container which contains the options
        ToggleGroup sameOptionGroup = new ToggleGroup();
        for(String text : question.getOptions()){
            RadioButton radioButton = new RadioButton();
            radioButton.setText(text);
            radioButton.getStyleClass().add("label-large");
            radioButton.getStyleClass().add("label-black");
            radioButton.setToggleGroup(sameOptionGroup);
            optionsContainer.getChildren().add(radioButton);
        }
        return optionsContainer;
    }

    private void quitAndSaveAssessment(){
        //TODO: save the assessment
        StudentSubmission submission = new StudentSubmission(UserService.getCurrentUser().getId(), getAnswers());
        EvaluationResult result = StudentAssessmentService.submit(assessment.getId(), submission);
        if (isExam) timer.stop();
        try{
            parentController.makeAssessmentAndResultContainerVisible();
            parentController.showAssessmentResult(assessment.getSubject(), result);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<String> getAnswers(){
        List<String> answers = new ArrayList<>();
        for (VBox vBox : questionsContainer) {
            VBox optionsContainer = (VBox) vBox.getChildren().get(optionsContainerIndex);
            ToggleGroup toggleGroup = ((ToggleButton) optionsContainer.getChildren().getFirst()).getToggleGroup();
            ToggleButton toggleButton = (ToggleButton) toggleGroup.getSelectedToggle();
            if (toggleButton != null )answers.add(toggleButton.getText());
            else answers.add(null);
        }
        return answers;
    }

    public void setParentController(MainLayoutControllerForStudents parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void nextQuestion(){
        if ( currentQuestionIndex < questionsContainer.size() - 1){
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
        }

    }

    @FXML
    private void previousQuestion(){
        if ( currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion(currentQuestionIndex);
        }
    }

    @FXML
    void finishAssessment(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure, do you want finish the assessment?");
        alert.setContentText("Do you want to proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) quitAndSaveAssessment();

    }
}