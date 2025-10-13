package com.quiz_geek.controllers.student;

import com.quiz_geek.models.Assessment;
import com.quiz_geek.models.AssessmentResult;
import com.quiz_geek.services.core.AssessmentService;
import com.quiz_geek.services.student.AssessmentResultService;
import com.quiz_geek.services.student.StudentAssessmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.Random;

public class MyResultController {

    @FXML private ScrollPane rootScroll;
    @FXML private TableView<AssessmentResult> resultsTable;
    @FXML private TableColumn<AssessmentResult, String> colSubject;
    @FXML private TableColumn<AssessmentResult, String> colTitle;
    @FXML private TableColumn<AssessmentResult, String> colDifficulty;
    @FXML private TableColumn<AssessmentResult, Integer> colTotalQuestions;
    @FXML private TableColumn<AssessmentResult, Integer> colCorrectQuestions;
    @FXML private TableColumn<AssessmentResult, Double> colResult;
    @FXML private TableColumn<AssessmentResult, Void> colAction;

    private StudentAssessmentService studentAssessmentService =  StudentAssessmentService.getInstance();
    private MainLayoutControllerForStudents controller;

    @FXML
    public void initialize() {
        // Add stylesheets programmatically to avoid Scene Builder compatibility issues
        if (rootScroll != null) {
            rootScroll.getStylesheets().addAll(
                    "/com/quiz_geek/styles/style.css",
                    "/com/quiz_geek/CssFiles/style.css"
            );
        }
        AssessmentResultService.getInstance().addAssessmentResultList();
        setupTable();
        populateTable();
        setupRowInteractivity();
    }

    private void setupTable() {
        // Set up cell value factories
        colSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("Difficulty"));
        colTotalQuestions.setCellValueFactory(new PropertyValueFactory<>("Total Questions"));
        colCorrectQuestions.setCellValueFactory(new PropertyValueFactory<>("Correct Questions"));

        // ADD THIS LINE - Missing cell value factory for result column
        colResult.setCellValueFactory(new PropertyValueFactory<>("Result")); // or whatever the property name is
        
        // Custom cell factory for result column (progress bar)
        colResult.setCellFactory(column -> new TableCell<AssessmentResult, Double>() {
            private final ProgressBar progressBar = new ProgressBar();
            
            @Override
            protected void updateItem(Double percentage, boolean empty) {
                super.updateItem(percentage, empty);
                if (empty || percentage == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(percentage / 100.0);
                    progressBar.setPrefWidth(120);
                    progressBar.setPrefHeight(13);
                    progressBar.setMinHeight(13);
                    
                    // Color coding based on performance
                    if (percentage >= 80) {
                        progressBar.setStyle("-fx-accent: #10B981;"); // Green
                    } else if (percentage >= 60) {
                        progressBar.setStyle("-fx-accent: #F59E0B;"); // Orange
                    } else {
                        progressBar.setStyle("-fx-accent: #EF4444;"); // Red
                    }
                    
                    setGraphic(progressBar);
                }
            }
        });
        
        // Custom cell factory for action column (retake button)
        colAction.setCellFactory(column -> new TableCell<AssessmentResult, Void>() {
            private final Button retakeButton = new Button("Retake");
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AssessmentResult result = getTableView().getItems().get(getIndex());
                    retakeButton.setOnAction(e -> handleRetake(result));
                    retakeButton.setPrefWidth(80);
                    retakeButton.setPrefHeight(30);
                    retakeButton.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 4px; -fx-padding: 2 4");
                    setGraphic(retakeButton);
                }
            }
        });
        
        // Style the table
        styleTable();
    }
    
    private void styleTable() {
        // Set row height
        resultsTable.setFixedCellSize(50);
        
        // Style table headers
        resultsTable.getStyleClass().add("styled-table");
        
        // Add custom CSS for table styling
        String tableStyle = """
            .styled-table {
                -fx-background-color: white;
                -fx-border-color: #E5E7EB;
                -fx-border-width: 1px;
                -fx-border-radius: 8px;
            }
            .styled-table .column-header-background {
                -fx-background-color: #F9FAFB;
                -fx-border-color: #E5E7EB;
                -fx-border-width: 0 0 1px 0;
            }
            .styled-table .column-header {
                -fx-background-color: transparent;
                -fx-border-color: transparent;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-text-fill: #374151;
                -fx-padding: 12px 8px;
            }
            .styled-table .table-cell {
                -fx-border-color: transparent;
                -fx-font-size: 13px;
                -fx-text-fill: #374151;
                -fx-padding: 12px 8px;
                -fx-alignment: center-left;
            }
            .styled-table .table-row-cell {
                -fx-background-color: white;
                -fx-border-color: transparent;
            }
            .styled-table .table-row-cell:odd {
                -fx-background-color: #F9FAFB;
            }
            .styled-table .table-row-cell:hover {
                -fx-background-color: #F3F4F6;
            }
            .styled-table .table-row-cell:selected {
                -fx-background-color: #DBEAFE;
                -fx-text-fill: #1E40AF;
            }
            """;
        
        rootScroll.getStylesheets().add("data:text/css," + tableStyle);
    }

    private void populateTable() {
        try {
            // Fetch assessments from service
            List<?> assessments = StudentAssessmentService.getInstance().getAllAssessments();
            
            ObservableList<AssessmentResult> results = FXCollections.observableArrayList();
            Random random = new Random();
            
            // Generate random data for up to 15 rows
            String[] subjects = {"Maths", "Physics", "Chemistry", "Biology", "English", "History", "Geography"};
            String[] difficulties = {"Easy", "Medium", "Hard"};
            
            int maxRows = Math.min(assessments.size(), 15);
            for (int i = 0; i < maxRows; i++) {
                Assessment assessment = (Assessment) assessments.get(i);
                String subject = subjects[random.nextInt(subjects.length)];
                String title = "Test-" + (i + 1);
                String difficulty = difficulties[random.nextInt(difficulties.length)];
                int totalQuestions = 10 + random.nextInt(20); // 10-30 questions
                int correctQuestions = random.nextInt(totalQuestions + 1); // 0 to totalQuestions

                results.add(new AssessmentResult(subject, title, difficulty, totalQuestions, correctQuestions, assessment));
            }
            
            resultsTable.setItems(results);
        } catch (Exception e) {
            // Fallback: create sample data if service fails
            ObservableList<AssessmentResult> sampleResults = FXCollections.observableArrayList(
                AssessmentResultService.getInstance().getAssessmentResultList());
            resultsTable.setItems(sampleResults);
        }
    }

    private void setupRowInteractivity() {
        resultsTable.setRowFactory(tv -> {
            TableRow<AssessmentResult> row = new TableRow<>();
            
            // Hover effect
            row.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered) {
                    row.getStyleClass().add("row-hover");
                } else {
                    row.getStyleClass().remove("row-hover");
                }
            });
            
            // Selection effect
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.getStyleClass().add("row-selected");
                } else {
                    row.getStyleClass().remove("row-selected");
                }
            });
            
            return row;
        });
        
        // Enable single selection
        resultsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void handleRetake(AssessmentResult result) {
        try {
            if (result.getAssessment() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Assessment not available");
                alert.setContentText("This assessment cannot be retaken at this time.");
                alert.showAndWait();
                return;
            }

            if (controller != null) {
                controller.showTakeAssessment(result.getAssessment());
            }
        } catch (Exception e) {
            // Show error dialog or log
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to start assessment");
            alert.setContentText("Please try again later.");
            alert.showAndWait();
        }
    }

    public void setParentController(MainLayoutControllerForStudents controller){
        this.controller = controller;
    }
}
