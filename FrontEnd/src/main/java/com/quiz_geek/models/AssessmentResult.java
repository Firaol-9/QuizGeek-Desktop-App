package com.quiz_geek.models;

public class AssessmentResult {
    private final String subjectName;
    private final String title;
    private final String difficulty;
    private final int totalQuestions;
    private final int correctQuestions;
    private final double percentage;
    private final Assessment assessment;

    public AssessmentResult(String subjectName, String title, String difficulty, 
                          int totalQuestions, int correctQuestions, Assessment assessment) {
        this.subjectName = subjectName;
        this.title = title;
        this.difficulty = difficulty;
        this.totalQuestions = totalQuestions;
        this.correctQuestions = correctQuestions;
        this.percentage = totalQuestions > 0 ? (double) correctQuestions / totalQuestions * 100 : 0;
        this.assessment = assessment;
    }

    public String getSubjectName() { return subjectName; }
    public String getTitle() { return title; }
    public String getDifficulty() { return difficulty; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectQuestions() { return correctQuestions; }
    public double getPercentage() { return percentage; }
    public Assessment getAssessment() { return assessment; }

    public Double getResult() {
        if (totalQuestions > 0) {
            return (correctQuestions * 100.0) / totalQuestions;
        }
        return 0.0;
    }
}
