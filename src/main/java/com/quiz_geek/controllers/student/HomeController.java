package com.quiz_geek.controllers.student;

import com.quiz_geek.models.Exam;
import com.quiz_geek.models.QuestionsAccessibility;
import com.quiz_geek.utils.Constants;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.UIHelpers;
import com.quiz_geek.models.Assessment;
import com.quiz_geek.services.student.StudentAssessmentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML TilePane tilePane;
    @FXML ToggleButton practiceQuestionsTab;
    @FXML ToggleButton worksheetsTab;
    @FXML ToggleButton examsTab;
    @FXML ToggleButton allQuestionsTab;

    private final ToggleGroup navGroup = new ToggleGroup();
    MainLayoutControllerForStudents mainLayoutController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tilePane.setPrefTileWidth(-1);//-1 is to make the tilePane automatically adjust the width and height of the cards
        tilePane.setPrefTileHeight(-1);
        tilePane.setMaxWidth(Double.MAX_VALUE);
        tilePane.setMaxHeight(Double.MAX_VALUE);
        tilePane.setHgap(20);
        tilePane.setVgap(20);

        StudentAssessmentService.addAssessments();
        for(Assessment a : StudentAssessmentService.getInstance().getAllAssessments()){
            addAssessmentCard(a);
        }

        practiceQuestionsTab.setToggleGroup(navGroup);
        worksheetsTab.setToggleGroup(navGroup);
        examsTab.setToggleGroup(navGroup);
        allQuestionsTab.setToggleGroup(navGroup);

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
        Label numOfQuestions = new Label(assessment.getQuestions().size() + "");
        Label accessibility = new Label(assessment.getAccessibility().toString());
        Button btn = new Button("Start");

        boolean isAssessmentExam = assessment instanceof Exam;
        UIHelpers.nodeVisibility(timeLimit, isAssessmentExam);

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

        if (isAssessmentExam){
            Exam exam = (Exam) assessment;
            timeLimit.setText(exam.getTimeLimitInMinutes() + "mins");
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

        labelsContainer.setSpacing(50);
        labelsContainer.getChildren().addAll(numOfQuestions, timeLimit, accessibility);

        vbox.setSpacing(30);
        vbox.getChildren().addAll(subjectAndDifficultyContainer, title, labelsContainer);

        buttonContainer.setPadding(new Insets(10, 10, 10, 10));
        buttonContainer.getChildren().add(btn);
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setMaxWidth(Double.MAX_VALUE);

        btn.setOnAction(event-> onCardClicked(assessment) );

        card.getChildren().addAll(vbox, buttonContainer);
        tilePane.getChildren().add(card);
    }

    public void setMainContainer(MainLayoutControllerForStudents mainLayoutController){
        this.mainLayoutController = mainLayoutController;
    }

    private void onCardClicked(Assessment assessment){
        if ( assessment.getAccessibility() == QuestionsAccessibility.PUBLIC)
            mainLayoutController.showTakeAssessment(assessment);
        else
            handleAssessmentClick(assessment);
    }

    private void handleAssessmentClick(Assessment assessment) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/ForStudents/privateAssessmentPasswordPrompt.fxml"));
            Parent root = loader.load();

            privateAssessmentPasswordPromptController controller = loader.getController();
            controller.setDetails(assessment.getAssessmentTitle(), assessment.getPassword(), () -> {
                mainLayoutController.showTakeAssessment(assessment);
            });

            Stage mainStage = (Stage) allQuestionsTab.getScene().getWindow();
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initOwner(mainStage);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void showAllQuestions(){
        tilePane.getChildren().clear();
        for(Assessment a : StudentAssessmentService.getInstance().getAllAssessments()){
            addAssessmentCard(a);
        }
    }

    @FXML
    void showPracticeQuestions(){
        tilePane.getChildren().clear();
        for(Assessment a : StudentAssessmentService.getInstance().getPracticeQuestions()){
            addAssessmentCard(a);
        }

    }

    @FXML
    void showWorkSheets(){
        tilePane.getChildren().clear();
        for(Assessment a : StudentAssessmentService.getInstance().getWorksheets()){
            addAssessmentCard(a);
        }
    }

    @FXML
    void showExams(){
        tilePane.getChildren().clear();
        for(Assessment a : StudentAssessmentService.getInstance().getExams()){
            addAssessmentCard(a);
        }
    }
}
