package com.quiz_geek.controllers.student;


import com.quiz_geek.models.AssessmentFeedback;
import com.quiz_geek.models.EvaluationResult;
import com.quiz_geek.models.Exam;
import com.quiz_geek.services.student.StudentAssessmentService;
import com.quiz_geek.utils.Constants;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.Helpers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AssessmentResultController {
    @FXML VBox labelAndErrorsContainer;
    @FXML VBox feedbacksContainer;
    @FXML Label yourScoreLabel;
    @FXML Label scoreLabel;
    @FXML Label subjectName;
    @FXML Label percentLabel;
    @FXML ProgressBar progressBar;
    MainLayoutControllerForStudents parentController;

    @FXML
    void initialize(){

        try{
            yourScoreLabel.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/checkCircle.svg", 27, 30, Constants.accentColor));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void setParentController(MainLayoutControllerForStudents parentController){
        this.parentController = parentController;
    }

    private VBox makeFeedbackBox(String assessmentId, AssessmentFeedback feedback, int index){
        VBox feedBackContainer = new VBox();
        String questionText = StudentAssessmentService.getInstance().getAssessment(assessmentId).getQuestion(index).getQuestionText();
        String correctAnswer = feedback.correctAnswer();
        String studentAnswer = feedback.studentAnswer();

        Label questionTextLabel = new Label( (index + 1) + "." + questionText);
        Label correctAnswerLabel = new Label("Correct answer: " + correctAnswer);
        Label studentAnswerLabel = new Label("Your answer: " + studentAnswer);

        questionTextLabel.getStyleClass().addAll("label-medium", "label-semiBold");
        correctAnswerLabel.getStyleClass().addAll("label-medium", "label-semiBold");
        correctAnswerLabel.setPadding(new Insets(0, 0, 0, 16));
        studentAnswerLabel.getStyleClass().addAll("label-medium", "label-semiBold");
        studentAnswerLabel.setPadding(new Insets(0, 0, 0, 16));

        boolean isQuestionCorrect = feedback.isCorrect();
        if (isQuestionCorrect){
            Helpers.nodeVisibility(studentAnswerLabel, false);
            feedBackContainer.getStyleClass().addAll("section-card", "section-card-feedback-green");
        }
        else{
            Helpers.nodeVisibility(studentAnswerLabel, true);
            feedBackContainer.getStyleClass().addAll("section-card", "section-card-feedback-red");
        }
        try{
            if (!isQuestionCorrect)
                studentAnswerLabel.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/remove.svg", 20, 20, Color.RED));
            correctAnswerLabel.setGraphic(SvgLoader.loadSvg("/com/quiz_geek/Icons/right.svg", 20, 20, Color.GREEN));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        feedBackContainer.setSpacing(8);
        feedBackContainer.getChildren().addAll(questionTextLabel, correctAnswerLabel, studentAnswerLabel);
        return feedBackContainer;
    }

    protected void setUp(String subject, EvaluationResult result){
        int score = result.getScore();
        int total = result.getTotal();
        float percent = ( (float) score/ total) * 100;
        boolean isExam = StudentAssessmentService.getInstance().getAssessment(result.getAssessmentId()) instanceof Exam;

        scoreLabel.setText(result.toString());
        subjectName.setText(subjectName.getText() + subject);
        percentLabel.setText(String.format("%.1f", percent) + "%");
        progressBar.setProgress(percent/100);

        if (percent >= 80) { progressBar.getStyleClass().add("high-score");}
        else if (percent >= 50) { progressBar.getStyleClass().add("medium-score"); }
        else { progressBar.getStyleClass().add("low-score"); }

        if (isExam){
            Helpers.nodeVisibility(labelAndErrorsContainer, false);
            Helpers.nodeVisibility(progressBar, false);
            Helpers.nodeVisibility(percentLabel, false);
        }

        if (!isExam) {
            for (int i = 0; i < result.getAssessmentFeedbacks().size(); i++) {
                feedbacksContainer.getChildren().add(makeFeedbackBox(result.getAssessmentId(), result.getAssessmentFeedbacks().get(i), i));
            }
        }
    }

    @FXML
    private void backToHome(){
        try{
            parentController.makeMainLayoutVisible();
            parentController.showHome();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
