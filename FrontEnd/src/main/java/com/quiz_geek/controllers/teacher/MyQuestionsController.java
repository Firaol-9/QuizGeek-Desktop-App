package com.quiz_geek.controllers.teacher;

import com.quiz_geek.models.*;
import com.quiz_geek.utils.Constants;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.Helpers;
import com.quiz_geek.services.teacher.TeacherAssessmentService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyQuestionsController implements Initializable {

    @FXML TilePane tilePane;
    @FXML ToggleButton practiceQuestionsTab;
    @FXML ToggleButton examsTab;
    @FXML ToggleButton worksheetsTab;
    @FXML ToggleButton allQuestionsTab;
    @FXML ScrollPane scrollPane;
    private final ToggleGroup navGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tilePane.setPrefTileWidth(-1);//-1 is to make the tilePane automatically adjust the width and height of the cards
        tilePane.setPrefTileHeight(-1);
        tilePane.setMaxWidth(Double.MAX_VALUE);
        tilePane.setMaxHeight(Double.MAX_VALUE);
        tilePane.setHgap(35);
        tilePane.setVgap(20);

        practiceQuestionsTab.setToggleGroup(navGroup);
        examsTab.setToggleGroup(navGroup);
        worksheetsTab.setToggleGroup(navGroup);
        allQuestionsTab.setToggleGroup(navGroup);

        List<String> options = new ArrayList<>();
        options.add("a");
        options.add("b");

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What?", options, "a"));

        AssessmentDifficulty d = AssessmentDifficulty.HARD;
        AssessmentType t = AssessmentType.WORKSHEET;
        AssessmentType t2 = AssessmentType.EXAM;
        AssessmentType t3 = AssessmentType.PRACTICEQUESTION;
        AssessmentAccessibility a = AssessmentAccessibility.PUBLIC;

        for (int i = 0; i < 3; i++) {
            TeacherAssessmentService.addAssessment("Test-1", "Biology", questions, d, t, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "Maths", questions, d, t, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "Chemistry", questions, d, t, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "History", questions, d, t, a, "", "");
        }

        for (int i = 0; i < 3; i++) {
            TeacherAssessmentService.addAssessment("Test-1", "Biology", questions, d, t3, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "Maths", questions, d, t3, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "Chemistry", questions, d, t3, a, "", "");
            TeacherAssessmentService.addAssessment("Test-1", "History", questions, d, t3, a, "", "");
        }


        for (int i = 0; i < TeacherAssessmentService.getInstance().getAllAssessments().size(); i++) {
            Assessment assessment = TeacherAssessmentService.getInstance().getAllAssessments().get(i);
            addAssessmentCard(assessment);
        }

        navGroup.selectToggle(allQuestionsTab);

        navGroup.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel == null && oldSel != null) {
                oldSel.setSelected(true);
            }
        });
    }

    void addAssessmentCard(Assessment assessment){
        int cardWidth = 250;
        int cardHeight = 200;
        int cardPadding = 15;
        VBox card = new VBox();
        VBox vbox = new VBox();
        HBox subjectAndDifficultyContainer = new HBox();
        HBox subjectContainer = new HBox();
        HBox difficultyContainer = new HBox();
        HBox labelsContainer = new HBox();
        HBox buttonContainer = new HBox();

        Label subjectName = new Label(assessment.getSubject());
        Label title = new Label(assessment.getAssessmentTitle());
        Label difficulty = new Label(assessment.getDifficulty().toString());
        Label timeLimit = new Label();
        Label numOfQuestions = new Label("" + assessment.getQuestions().size());
        Label accessibility = new Label(assessment.getAccessibility().toString());
        Button btn = new Button("Edit");

        boolean isAssessmentExam = assessment instanceof Exam;
        Helpers.nodeVisibility(timeLimit, isAssessmentExam);

        card.setSpacing(10);
        card.setPadding(new Insets(cardPadding, cardPadding, cardPadding,cardPadding));
        card.getStyleClass().add("card");
        card.setPrefHeight(cardHeight);
        card.setPrefWidth(cardWidth);

        subjectContainer.getChildren().add(subjectName);

        difficultyContainer.getChildren().add(difficulty);
        difficultyContainer.setAlignment(Pos.TOP_RIGHT);

        subjectAndDifficultyContainer.getChildren().addAll(subjectContainer, difficultyContainer);
        HBox.setHgrow(difficultyContainer, Priority.ALWAYS);
        HBox.setHgrow(subjectContainer, Priority.ALWAYS);


        labelsContainer.setSpacing(15);

        title.getStyleClass().add("label-medium");
        title.getStyleClass().add("label-bold");

        if ( isAssessmentExam){
            Exam exam = (Exam) assessment;
            timeLimit.setText(exam.getTimeLimitInMinutes() + " min");
        }
        timeLimit.getStyleClass().add("label-verySmall");

        numOfQuestions.getStyleClass().add("label-verySmall");
        accessibility.getStyleClass().add("label-verySmall");

        try{
            if (isAssessmentExam) timeLimit.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/timer.svg", 20, 20, Constants.secondaryColor));
            numOfQuestions.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/questionMark.svg", 20, 20, Constants.secondaryColor));
            accessibility.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/lock.svg", 20 , 20, Constants.secondaryColor));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        labelsContainer.getChildren().addAll(numOfQuestions, timeLimit, accessibility);

        vbox.setSpacing(30);
        vbox.getChildren().addAll(subjectAndDifficultyContainer, title, labelsContainer);

        buttonContainer.setPadding(new Insets(10, 10, 10, 10));
        buttonContainer.getChildren().add(btn);
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setMaxWidth(Double.MAX_VALUE);


        card.getChildren().addAll(vbox, buttonContainer);
        tilePane.getChildren().add(card);
    }

    @FXML
    private void showAllAssessments(){
        tilePane.getChildren().clear();
        for (int i = 0; i < TeacherAssessmentService.getInstance().getAllAssessments().size(); i++) {
            Assessment assessment = TeacherAssessmentService.getInstance().getAllAssessments().get(i);
            addAssessmentCard(assessment);
        }
    }

    @FXML
    void showPracticeQuestions(){
        tilePane.getChildren().clear();
        for (int i = 0; i < TeacherAssessmentService.getInstance().getPracticeQuestions().size(); i++) {
            Assessment assessment = TeacherAssessmentService.getInstance().getPracticeQuestions().get(i);
            addAssessmentCard(assessment);
        }
    }

    @FXML
    void showWorkSheets(){
        tilePane.getChildren().clear();
        for (int i = 0; i < TeacherAssessmentService.getInstance().getWorksheets().size(); i++) {
            Assessment assessment = TeacherAssessmentService.getInstance().getWorksheets().get(i);
            addAssessmentCard(assessment);
        }
    }

    @FXML
    void showExams(){
        tilePane.getChildren().clear();
        for (int i = 0; i < TeacherAssessmentService.getInstance().getExams().size(); i++) {
            Assessment assessment = TeacherAssessmentService.getInstance().getExams().get(i);
            addAssessmentCard(assessment);
        }
    }
}
